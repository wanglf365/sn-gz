/*
 * Copyright (C), 2018-2019, 深圳十年技术有限公司
 * FileName: OrgServiceImpl
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.dsc.service;

import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.core.BusinessException;
import com.sn.gz.core.sandbox.UserContext;
import com.sn.gz.core.utils.IdGenerator;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.core.utils.Md5Utils;
import com.sn.gz.dscstarter.utils.AnnotationConstants;
import com.sn.gz.pmp.api.dto.org.*;
import com.sn.gz.pmp.api.inter.OrgService;
import com.sn.gz.pmp.dsc.bo.org.DepartmentBO;
import com.sn.gz.pmp.dsc.constants.ErrorMessage;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.Department;
import com.sn.gz.pmp.dsc.entity.org.Group;
import com.sn.gz.pmp.dsc.entity.org.Role;
import com.sn.gz.pmp.dsc.entity.org.RoleTeam;
import com.sn.gz.pmp.dsc.manager.org.OrgManager;
import com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel;
import com.sn.gz.pmp.dsc.model.org.MemberOutModel;
import com.sn.gz.pmp.dsc.model.org.RoleOutModel;
import com.sn.gz.pmp.dsc.transfer.OrgTransfer;
import com.sn.gz.pmp.dsc.utils.OrgUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 组织架构接口实现
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Slf4j
@Component
@Service(version = AnnotationConstants.VERSION, filter = "contextProviderFilter", timeout = 20000)
public class OrgServiceImpl implements OrgService {

    @Resource
    private OrgManager orgManager;

    @Override
    public void addRole(RoleUpdateInDTO roleUpdateInDTO) throws BusinessException {
        Date date = new Date();
        RoleTeam roleTeam = orgManager.getRoleTeam(roleUpdateInDTO.getRoleTeamId());
        if (OrgConstants.RoleType.PROJECT.equals(roleTeam.getType())) {
            throw new BusinessException(ErrorMessage.PROJECT_ROLE_ADD_NOT_ALLOWED);
        }
        Long groupId = UserContext.getContextGroupId();
        Role role = OrgTransfer.INSTANCE.map(roleUpdateInDTO);
        role.setId(IdGenerator.getInstance().nextId());
        role.setAdmin(OrgConstants.RoleAdmin.NOT_ADMIN);
        role.setEnable(OrgConstants.RoleIsEnable.ENABLE);
        role.setGroupId(groupId);
        role.setRoleTeamId(roleUpdateInDTO.getRoleTeamId());
        role.setCreateDatetime(date);
        role.setModifiedDatetime(date);

        orgManager.addRole(role);
    }

    @Override
    public void updateRole(RoleUpdateInDTO roleUpdateInDTO) throws BusinessException {
        Date date = new Date();
        Role role = OrgTransfer.INSTANCE.map(roleUpdateInDTO);
        role.setModifiedDatetime(date);

        orgManager.updateRole(role);
    }

    @Override
    public DepartmentOutDTO getDepartmentTree(String keyword) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        return OrgTransfer.INSTANCE.map(orgManager.getDepartmentTree(groupId, keyword));
    }

    @Override
    public void updateMainDepartment(Long memberId, Long departmentId) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        orgManager.updateMainDepartment(groupId, memberId, departmentId);
    }

    @Override
    public List<DepartmentMemberOutDTO> listDepartmentMember(Long departmentId) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();

        List<DepartmentMemberOutModel> departmentMemberOutModelList = orgManager.listDepartmentMember(groupId);
        if (ListUtils.isNull(departmentMemberOutModelList)) {
            return null;
        }

        List<DepartmentMemberOutDTO> departmentMemberOutDTOList;
        Map<Long, DepartmentMemberOutDTO> map = new HashMap<>();
        for (DepartmentMemberOutModel item : departmentMemberOutModelList) {
            DepartmentMemberOutDTO departmentMemberOutDTO = map.get(item.getMemberId());
            {
                if (null == departmentMemberOutDTO) {
                    departmentMemberOutDTO = new DepartmentMemberOutDTO(item.getMemberId(), item.getMemberName(), item.getPhone());
                    List<MemberDepartmentOutDTO> departmentList = new ArrayList<>();
                    MemberDepartmentOutDTO memberDepartmentOutDTO = new MemberDepartmentOutDTO(item.getDepartmentName(), item.getMainDepartment(), item.getDepartmentId());
                    departmentList.add(memberDepartmentOutDTO);
                    departmentMemberOutDTO.setDepartmentList(departmentList);
                    map.put(item.getMemberId(), departmentMemberOutDTO);
                } else {
                    MemberDepartmentOutDTO memberDepartmentOutDTO = new MemberDepartmentOutDTO(item.getDepartmentName(), item.getMainDepartment(), item.getDepartmentId());
                    departmentMemberOutDTO.getDepartmentList().add(memberDepartmentOutDTO);
                }
            }
        }
        departmentMemberOutDTOList = ListUtils.map2List(map);
        return excludeMember(departmentMemberOutDTOList, departmentId);
    }

    /**
     * 排除不在此部门的成员，
     * 因为从成员反选部门，sql不能实现
     *
     * @param departmentMemberOutDTOList list
     * @param departmentId               部门id
     */
    private List<DepartmentMemberOutDTO> excludeMember(List<DepartmentMemberOutDTO> departmentMemberOutDTOList, Long departmentId) throws BusinessException {
        if (ListUtils.isNull(departmentMemberOutDTOList)) {
            return null;
        }

        boolean flag;
        List<DepartmentMemberOutDTO> list = new ArrayList<>();
        for (DepartmentMemberOutDTO item : departmentMemberOutDTOList) {
            flag = false;
            List<MemberDepartmentOutDTO> departmentList = item.getDepartmentList();
            if (ListUtils.isNotNull(departmentList)) {
                for (MemberDepartmentOutDTO memberDepartmentOutDTO : departmentList) {
                    if (departmentId.longValue() == memberDepartmentOutDTO.getDepartmentId().longValue()) {
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    list.add(item);
                }
            }
        }
        return list;
    }

    @Override
    public DepartmentRoleOutDTO listMemberByExcludeRole(Long roleId, String keyword) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        List<DepartmentMemberOutModel> departmentMemberOutModelList = orgManager.listMemberByExcludeRole(groupId, roleId, keyword);
        if (ListUtils.isNull(departmentMemberOutModelList)) {
            return null;
        }

        List<Department> departmentList = orgManager.getDepartmentByGroupId(groupId);
        if (ListUtils.isNull(departmentList)) {
            return null;
        }
        DepartmentBO departmentBO = OrgUtils.listToTreeAndMember(departmentList, departmentMemberOutModelList);
        return OrgTransfer.INSTANCE.mapDepartmentRole(departmentBO);
    }

    @Override
    public void deleteRole(Long roleId) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        orgManager.deleteRole(roleId, groupId);
    }

    @Override
    public List<RoleTeamInquiryOutDTO> inquiryRole(String keyword) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();

        List<RoleOutModel> roleOutModelList = orgManager.inquiryRole(groupId, keyword);
        if (ListUtils.isNull(roleOutModelList)) {
            return null;
        }

        List<RoleTeamInquiryOutDTO> roleTeamInquiryOutDTOList;
        Map<Long, RoleTeamInquiryOutDTO> map = new HashMap<>();
        for (RoleOutModel item : roleOutModelList) {
            RoleTeamInquiryOutDTO roleTeamInquiryOutDTO = map.get(item.getRoleTeamId());
            if (roleTeamInquiryOutDTO == null) {
                roleTeamInquiryOutDTO = new RoleTeamInquiryOutDTO();
                roleTeamInquiryOutDTO.setRoleTeamId(item.getRoleTeamId());
                roleTeamInquiryOutDTO.setRoleTeamName(item.getRoleTeamName());
                List<RoleInquiryOutDTO> roleList = new ArrayList<>();
                RoleInquiryOutDTO role = new RoleInquiryOutDTO();
                role.setRoleId(item.getRoleId());
                role.setRoleName(item.getRoleName());
                roleList.add(role);
                roleTeamInquiryOutDTO.setRoleList(roleList);
                map.put(item.getRoleTeamId(), roleTeamInquiryOutDTO);
            } else {
                RoleInquiryOutDTO role = new RoleInquiryOutDTO();
                role.setRoleId(item.getRoleId());
                role.setRoleName(item.getRoleName());
                roleTeamInquiryOutDTO.getRoleList().add(role);
            }
        }

        roleTeamInquiryOutDTOList = ListUtils.map2List(map);
        return roleTeamInquiryOutDTOList;
    }

    @Override
    public List<MemberOutDTO> listMemberRoleId(Long roleId) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        List<MemberOutModel> memberOutModelList = orgManager.listMemberRoleId(groupId, roleId);

        return OrgTransfer.INSTANCE.mapMemberOut(memberOutModelList);

    }

    @Override
    public List<RoleTeamOutDTO> listRoleTeam() throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        return OrgTransfer.INSTANCE.mapRoleTeam(orgManager.listByGroupId(groupId));
    }

    @Override
    public void addGroup(GroupUpdateInDTO groupUpdateInDTO) throws BusinessException {
        Date date = new Date();
        Group group = OrgTransfer.INSTANCE.map(groupUpdateInDTO);
        group.setId(IdGenerator.getInstance().nextId());
        group.setCreateDatetime(date);
        group.setModifiedDatetime(date);
        group.setStatus(OrgConstants.GroupStatus.ACTIVE);


        orgManager.addGroup(group);
    }

    @Override
    public void addUnionUser(UnionUserUpdateInDTO unionUserUpdateInDTO) throws BusinessException {
        Date date = new Date();
        unionUserUpdateInDTO.setPassword(Md5Utils.generatePasswordMD5((unionUserUpdateInDTO.getPassword())));
        UnionUser unionUser = OrgTransfer.INSTANCE.map(unionUserUpdateInDTO);

        unionUser.setId(IdGenerator.getInstance().nextId());
        unionUser.setEnable(OrgConstants.UnionUserIsEnable.ENABLE);
        unionUser.setCreateDatetime(date);
        unionUser.setModifiedDatetime(date);

        orgManager.addUnionUser(unionUser);
    }

    @Override
    public void synchronizeOrg() throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        orgManager.synchronizeOrg(groupId);
    }

    @Override
    public void deleteByMemberIdAndRoleId(List<Long> memberList, Long roleId) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        orgManager.deleteByMemberIdAndRoleId(memberList, groupId, roleId);
    }

    @Override
    public void addMemberRoleRelationship(List<Long> memberList, Long roleId) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        orgManager.addMemberRoleRelationship(memberList, groupId, roleId);
    }

    @Override
    public void addRoleTeam(String name) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        Date date = new Date();
        RoleTeam roleTeam = new RoleTeam();
        roleTeam.setId(IdGenerator.getInstance().nextId());
        roleTeam.setName(name);
        roleTeam.setGroupId(groupId);
        roleTeam.setCreateDatetime(date);
        roleTeam.setModifiedDatetime(date);

        orgManager.addRoleTeam(roleTeam);
    }
}
