/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: CustomerRoleMapper
 * Author:   Enma.ai
 * Date:     2018-08-27
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.entity.org.MemberRoleRelationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户角色关系表Mapper
 *
 * @author Enma.ai
 * 2018-08-27
 */
public interface MemberRoleRelationshipMapper extends BaseMapper<MemberRoleRelationship> {

    /**
     * 批量保存客户角色关系表
     *
     * @param customerRoleList 客户角色关系表
     * @author xiaoxueliang
     * 2018/8/3
     */
    void batchInsert(@Param("memberRoleList") List<MemberRoleRelationship> customerRoleList);


    /**
     * 批量删除角色成员关系表
     *
     * @param memberList 成员
     * @param groupId    集团id
     * @author xiaoxueliang
     * 2018/8/3
     */
    void batchDelete(@Param("memberList") List<Long> memberList, @Param("groupId") Long groupId);

    /**
     * 批量删除角色成员关系表
     *
     * @param memberList 成员
     * @param groupId    集团id
     * @author xiaoxueliang
     * 2018/8/3
     */
    void batchDeleteByRoleId(@Param("memberList") List<Long> memberList, @Param("groupId") Long groupId, @Param("roleId") Long roleId);
}