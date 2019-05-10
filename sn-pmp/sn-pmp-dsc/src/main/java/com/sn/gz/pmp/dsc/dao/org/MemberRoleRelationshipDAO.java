/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: CustomerRoleDAO
 * Author:   Enma.ai
 * Date:     2018-08-27
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.org.Department;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.entity.org.MemberRoleRelationship;
import com.sn.gz.pmp.dsc.mapper.org.MemberRoleRelationshipMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户角色关系
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Component
public class MemberRoleRelationshipDAO extends BaseDAO<MemberRoleRelationship, Long> {

    @Resource
    private MemberRoleRelationshipMapper memberRoleRelationshipMapper;


    @Override
    public BaseMapper<MemberRoleRelationship> getMapper() {
        return memberRoleRelationshipMapper;
    }

    /**
     * 批量保存成员角色关系表
     *
     * @param memberRoleRelationshipList 成员角色关系
     * @author lufeiwang
     * 2019/4/25
     */
    public void batchInsert(List<MemberRoleRelationship> memberRoleRelationshipList) {
        if (ListUtils.isNotNull(memberRoleRelationshipList)) {
            memberRoleRelationshipMapper.batchInsert(memberRoleRelationshipList);
        }
    }

    /**
     * 批量删除角色成员关系表
     *
     * @param memberList 成员
     * @param groupId    集团id
     * @author lufeiwang
     * 2018/8/3
     */
    public void batchDeleteByMemberId(List<Long> memberList, Long groupId) {
        if (ListUtils.isNull(memberList)) {
            return;
        }
        memberRoleRelationshipMapper.batchDelete(memberList, groupId);
    }

    /**
     * 根据角色id * @param groupId
 * @param roleId  集团id
     * void@param rlufeiwangoleI2019/4/29d  角色id
     */
    public void deleteByRoleId(Long groupId, Long roleId) {
        EntityClassWrapper<MemberRoleRelationship> ew = new EntityClassWrapper(MemberRoleRelationship.class);
        ew.eq("groupId", groupId).eq("roleId", roleId);
        memberRoleRelationshipMapper.delete(ew);
    }

    /**
     * 批量删除角色成员关系表
     *
     * @param memberList 成员
     * @param groupId    集团id
     * @author xiaoxueliang
     * 2018/8/3
     */
    public void batchDeleteByMemberIdAndRoleId(List<Long> memberList, Long groupId, Long roleId) {
        if (ListUtils.isNull(memberList)) {
            return;
        }
        memberRoleRelationshipMapper.batchDeleteByRoleId(memberList, groupId, roleId);
    }

}
