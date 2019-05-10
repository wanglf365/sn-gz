/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: OrgManager
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.dsc.manager.org;

import com.sn.gz.component.org.constants.OperationPermission;
import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.component.tpsbiz.dingtalk.bo.DingtalkDepartmentBO;
import com.sn.gz.component.tpsbiz.dingtalk.bo.DingtalkUserBO;
import com.sn.gz.component.tpsbiz.dingtalk.manager.DingtalkCommonManager;
import com.sn.gz.core.utils.IdGenerator;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.pmp.dsc.bo.org.DepartmentBO;
import com.sn.gz.pmp.dsc.bo.org.SynchronizeOrgBO;
import com.sn.gz.pmp.dsc.dao.auth.UnionUserDAO;
import com.sn.gz.pmp.dsc.dao.org.*;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.*;
import com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel;
import com.sn.gz.pmp.dsc.model.org.MemberOutModel;
import com.sn.gz.pmp.dsc.model.org.RoleOutModel;
import com.sn.gz.pmp.dsc.transfer.OrgTransfer;
import com.sn.gz.pmp.dsc.utils.OrgUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 组织架构维护
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Component
@Slf4j
public class OrgManager {

    @Resource
    private GroupDAO groupDAO;

    @Resource
    private DepartmentDAO departmentDAO;

    @Resource
    private MemberDAO memberDAO;

    @Resource
    private DepartmentMemberRelationshipDAO departmentMemberRelationshipDAO;

    @Resource
    private UnionUserDAO unionUserDAO;

    @Resource
    private AuthorityDAO authorityDAO;

    @Resource
    private MemberRoleRelationshipDAO memberRoleRelationshipDAO;

    @Resource
    private RoleDAO roleDAO;

    @Resource
    private RoleTeamDAO roleTeamDAO;

    @Resource
    private RoleAuthorityRelationshipDAO roleAuthorityRelationshipDAO;

    @Resource
    private DingtalkCommonManager dingtalkCommonManager;

    @Resource
    private BaseAuthorityDAO baseAuthorityDAO;


    /**
     * 初始化权限字典
     *
     * @author lufeiwang
     * 2019/5/9
     */
//    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void initBaseAuthority() {
        List<BaseAuthority> baseAuthorities = new ArrayList<>();
        for (OperationPermission operationPermission : OperationPermission.values()) {
            BaseAuthority baseAuthority = new BaseAuthority(operationPermission.getId(), operationPermission.getName(), operationPermission.getValue(),
                    operationPermission.getCategory(), operationPermission.getParent(),operationPermission.getPermissionType());
            baseAuthorities.add(baseAuthority);
            baseAuthorityDAO.batchSaveOrUpdate(baseAuthorities);
        }
    }

    /**
     * 新增集团
     *
     * @param group 集团信息
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void addGroup(Group group) {
        groupDAO.save(group);
        initGroup(group);
    }

    /**
     * 初始化集团
     *
     * @param group 集团信息
     * @author lufeiwang
     * 2019/5/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void initGroup(Group group) {
        //创建默认根部门
        initDepartment(group);

        //创建默认角色组
        initRoleTeam(group);

        //初始化权限
        initAuthority(group);
    }

    /**
     * 初始化部门
     *
     * @param group 集团信息
     * @author lufeiwang
     * 2019/5/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void initDepartment(Group group) {
        Date date = new Date();
        Department department = new Department.Builder().groupId(group.getId()).name(group.getName()).level(0)
                .modifiedDatetime(date).createDatetime(date).build();
        departmentDAO.save(department);
    }

    /**
     * 初始化权限
     *
     * @param group 集团信息
     * @author lufeiwang
     * 2019/5/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void initAuthority(Group group) {
        List<BaseAuthority> baseAuthorities = baseAuthorityDAO.selectAll();
        if (ListUtils.isNull(baseAuthorities)) {
            return;
        }
        Date date = new Date();

        List<Authority> authorities = new ArrayList<>();
        for (BaseAuthority baseAuthority : baseAuthorities) {
            Authority authority = OrgTransfer.INSTANCE.map(baseAuthority);
            authority.setGroupId(group.getId());
            authority.setCreateDatetime(date);
            authority.setCreateDatetime(date);
            authorities.add(authority);
        }

        authorityDAO.batchInsert(authorities);
    }

    /**
     * 初始化角色组
     *
     * @param group 集团信息
     * @author lufeiwang
     * 2019/5/7
     */
    @Transactional(rollbackFor = Exception.class)
    public void initRoleTeam(Group group) {
        Date date = new Date();
        RoleTeam groupRoleTeam = new RoleTeam(group.getId(),
                OrgConstants.RoleTeamName.GROUP_NAME, OrgConstants.RoleType.GROUP, date);
        groupRoleTeam.setId(IdGenerator.getInstance().nextId());

        RoleTeam projectRoleTeam = new RoleTeam(group.getId(),
                OrgConstants.RoleTeamName.PROJECT_NAME, OrgConstants.RoleType.PROJECT, date);
        projectRoleTeam.setId(IdGenerator.getInstance().nextId());

        roleTeamDAO.save(groupRoleTeam);

        roleTeamDAO.save(projectRoleTeam);
    }


    /**
     * 新增全局用户账户
     *
     * @param unionUser 全局用户账户
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUnionUser(UnionUser unionUser) {
        unionUserDAO.save(unionUser);
    }

    /**
     * 修改
     *
     * @param group 集团信息
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateGroup(Group group) {
        groupDAO.updateById(group);
    }

    /**
     * 查询
     *
     * @param id 主键
     * @return com.sn.gz.pmp.dsc.entity.org.Group
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(readOnly = true)
    public Group selectById(Long id) {
        return groupDAO.selectById(id);
    }

    /**
     * 返回部门树形结构
     *
     * @param groupId 集团id
     * @return com.sn.gz.pmp.dsc.bo.org.DepartmentBO
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(readOnly = true)
    public DepartmentBO getDepartmentTree(Long groupId, String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            List<Department> list = departmentDAO.listByGroupId(groupId);
            if (ListUtils.isNull(list)) {
                return null;
            }
            return OrgUtils.listToTree(list);
        } else {
            //全部
            List<Department> allList = departmentDAO.listByGroupId(groupId);
            if (ListUtils.isNull(allList)) {
                return null;
            }

            //符合条件的
            List<Department> selectList = departmentDAO.listByName(groupId, keyword);
            return OrgUtils.mergeDepartment(allList, selectList);
        }
    }

    /**
     * 返回部门
     *
     * @param groupId 集团id
     * @return com.sn.gz.pmp.dsc.bo.org.DepartmentBO
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(readOnly = true)
    public List<Department> getDepartmentByGroupId(Long groupId) {
        return departmentDAO.listByGroupId(groupId);
    }

    /**
     * 按名字模糊查询
     *
     * @param groupId 集团id
     * @param keyword 关键字
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/5/6
     */
    @Transactional(readOnly = true)
    public List<Department> listByName(Long groupId, String keyword) {
        return departmentDAO.listByName(groupId, keyword);
    }

    /**
     * 查询部门成员
     * 关系，所有
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    @Transactional(readOnly = true)
    public List<DepartmentMemberOutModel> listDepartmentMember(Long groupId) {
        return memberDAO.listDepartmentMember(groupId);
    }

    /**
     * 查询部门成员，排除在已知角色内的
     *
     * @param groupId 集团id
     * @param roleId  角色id
     * @param keyword 关键字
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    @Transactional(readOnly = true)
    public List<DepartmentMemberOutModel> listMemberByExcludeRole(Long groupId, Long roleId, String keyword) {
        return memberDAO.listMemberByExcludeRole(groupId, roleId, keyword);
    }

    /**
     * 更改主部门
     *
     * @param groupId      集团id
     * @param memberId     成员id
     * @param departmentId 部门id
     * @author lufeiwang
     * 2019/5/5
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMainDepartment(Long groupId, Long memberId, Long departmentId) {
        departmentMemberRelationshipDAO.updateMainDepartment(groupId, memberId, departmentId);
    }

    /**
     * 新增角色
     *
     * @param role 角色
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRole(Role role) {
        roleDAO.save(role);
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
        roleDAO.updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId, Long groupId) {
        roleDAO.deleteById(roleId);

        //删除角色成员关系
        memberRoleRelationshipDAO.deleteByRoleId(groupId, roleId);

        //删除角色权限关系
        roleAuthorityRelationshipDAO.deleteByRoleId(groupId, roleId);
    }

    @Transactional(readOnly = true)
    public List<Member> listByUnionId(Long unionId) {
        return memberDAO.listByUnionId(unionId);
    }

    /**
     * 新增角色组
     *
     * @param roleTeam 角色
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRoleTeam(RoleTeam roleTeam) {
        roleTeamDAO.save(roleTeam);
    }

    /**
     * 查询
     *
     * @param roleTeamId 角色组id
     * @author lufeiwang
     * 2019/4/25
     */
    @Transactional(rollbackFor = Exception.class)
    public RoleTeam getRoleTeam(Long roleTeamId) {
        return roleTeamDAO.selectById(roleTeamId);
    }

    /**
     * 获取全部角色组
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.RoleTeam>
     * @author lufeiwang
     * 2019/4/29
     */
    @Transactional(readOnly = true)
    public List<RoleTeam> listByGroupId(Long groupId) {
        return roleTeamDAO.listByGroupId(groupId);
    }

    /**
     * 批量删除角色成员关系
     *
     * @param memberList 成员
     * @param groupId    集团id
     * @param roleId     角色id
     * @author lufeiwang
     * 2019/4/29
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMemberIdAndRoleId(List<Long> memberList, Long groupId, Long roleId) {
        memberRoleRelationshipDAO.batchDeleteByMemberIdAndRoleId(memberList, groupId, roleId);
    }


    /**
     * 批量新增角色成员关系
     *
     * @param memberList 成员
     * @param roleId     角色id
     * @param groupId    集团id
     * @author lufeiwang
     * 2019/4/29
     */
    @Transactional(rollbackFor = Exception.class)
    public void addMemberRoleRelationship(List<Long> memberList, Long groupId, Long roleId) {
        if (ListUtils.isNull(memberList)) {
            return;
        }
        List<MemberRoleRelationship> memberRoleRelationshipList = new ArrayList<>();
        for (Long memberId : memberList) {
            MemberRoleRelationship memberRoleRelationship = new MemberRoleRelationship.Builder().id(IdGenerator.getInstance().nextId())
                    .roleId(roleId).memberId(memberId).groupId(groupId).build();
            memberRoleRelationshipList.add(memberRoleRelationship);

        }
        memberRoleRelationshipDAO.batchInsert(memberRoleRelationshipList);
    }

    /**
     * 获取当前用户的所有权限的类型判断加载菜单
     *
     * @param memberId 用户id
     * @param groupId  集团id
     * @return java.util.List<java.lang.String>
     * @author lufeiwang
     * 2019/5/8
     */
    @Transactional(readOnly = true)
    public List<String> listMemberPermissionType(Long memberId, Long groupId) {
        return authorityDAO.listMemberPermissionType(memberId, groupId);
    }

    /**
     * 按关键字查询角色列表
     *
     * @param groupId 集团id
     * @param keyword 关键字
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.RoleOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    @Transactional(readOnly = true)
    public List<RoleOutModel> inquiryRole(Long groupId, String keyword) {
        return roleDAO.listRole(groupId, keyword);
    }

    /**
     * 根据角色id查询成员
     *
     * @param groupId 集团id
     * @param roleId  角色id
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.MemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    @Transactional(readOnly = true)
    public List<MemberOutModel> listMemberRoleId(Long groupId, Long roleId) {
        return memberDAO.listByRoleId(groupId, roleId);

    }

    /**
     * 同步组织架构
     * 先默认从钉钉同步
     *
     * @param groupId 集团id
     * @author lufeiwang
     * 2019/4/26
     */
    @Transactional(rollbackFor = Exception.class)
    public void synchronizeOrg(Long groupId) {
        //从钉钉获取所有部门
        List<DingtalkDepartmentBO> dingtalkDepartmentList = dingtalkCommonManager.listDepartment(groupId, null, null);
        if (ListUtils.isNull(dingtalkDepartmentList)) {
            return;
        }

        //从钉钉获取所有成员
        List<DingtalkUserBO> dingtalkUserList = new ArrayList<>();
        for (DingtalkDepartmentBO dingtalkDepartmentBO : dingtalkDepartmentList) {
            dingtalkUserList.addAll(dingtalkCommonManager.listMemberByDepartment(groupId, null, dingtalkDepartmentBO.getId(), null, null));
        }

        //现有部门
        List<Department> departmentList = departmentDAO.listByGroupId(groupId);
        //现有成员
        List<Member> memberList = memberDAO.listByGroupId(groupId);
        //现有用户部门关系
        List<DepartmentMemberRelationship> departmentMemberRelationshipList = departmentMemberRelationshipDAO.listByGroupId(groupId);
        SynchronizeOrgBO synchronizeOrgBO = OrgUtils.getSynchronizeOrgBO(groupId, dingtalkDepartmentList, departmentList, memberList, dingtalkUserList, departmentMemberRelationshipList);

        //先删除
        departmentDAO.delete(groupId);
        memberDAO.delete(groupId);
        departmentMemberRelationshipDAO.delete(groupId);
        memberRoleRelationshipDAO.batchDeleteByMemberId(synchronizeOrgBO.getDeleteMembers(), groupId);

        //再插入
        departmentDAO.batchInsert(synchronizeOrgBO.getDepartments());
        memberDAO.batchInsert(synchronizeOrgBO.getMembers());
        departmentMemberRelationshipDAO.batchInsert(synchronizeOrgBO.getDepartmentMemberRelationships());
        unionUserDAO.batchInsert(synchronizeOrgBO.getUnionUsers());

    }
}
