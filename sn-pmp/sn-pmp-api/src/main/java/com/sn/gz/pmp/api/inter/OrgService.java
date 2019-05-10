/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: OrgService
 * Author:   lufeiwang
 * Date:     2019/4/25
 */
package com.sn.gz.pmp.api.inter;

import com.sn.gz.core.BusinessException;
import com.sn.gz.pmp.api.dto.org.*;

import java.util.List;

/**
 * 组织架构接口
 *
 * @author lufeiwang
 * 2019/4/25
 */
public interface OrgService {

    void addRole(RoleUpdateInDTO roleUpdateInDTO) throws BusinessException;

    void updateRole(RoleUpdateInDTO roleUpdateInDTO) throws BusinessException;

    void addGroup(GroupUpdateInDTO groupUpdateInDTO) throws BusinessException;

    void addUnionUser(UnionUserUpdateInDTO unionUserUpdateInDTO) throws BusinessException;

    void synchronizeOrg() throws BusinessException;

    void deleteByMemberIdAndRoleId(List<Long> memberList, Long roleId) throws BusinessException;

    List<RoleTeamOutDTO> listRoleTeam() throws BusinessException;

    void addMemberRoleRelationship(List<Long> memberList, Long roleId) throws BusinessException;

    void addRoleTeam(String name) throws BusinessException;

    void deleteRole(Long roleId) throws BusinessException;

    List<RoleTeamInquiryOutDTO> inquiryRole(String keyword) throws BusinessException;

    List<MemberOutDTO> listMemberRoleId(Long roleId) throws BusinessException;

    DepartmentOutDTO getDepartmentTree(String keyword) throws BusinessException;

    List<DepartmentMemberOutDTO> listDepartmentMember(Long departmentId) throws BusinessException;

    void updateMainDepartment(Long memberId, Long departmentId) throws BusinessException;

    DepartmentRoleOutDTO listMemberByExcludeRole(Long roleId, String keyword) throws BusinessException;
}
