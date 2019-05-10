/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DepartmentCustomerMapper
 * Author:   xiaoxueliang
 * Date:     2018-07-11
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户所在部门关系表Mapper
 *
 * @author xiaoxueliang
 * 2018-07-11
 */
public interface DepartmentMemberRelationshipMapper extends BaseMapper<DepartmentMemberRelationship> {

    /**
     * 批量保存部门与人员的关联关系
     *
     * @param departmentMemberRelationshipList 部门与人员关联信息
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/14
     */
    Integer batchInsert(@Param("departmentMemberList") List<DepartmentMemberRelationship> departmentMemberRelationshipList);

    /**
     * 批量保存部门与人员的关联关系
     *
     * @param list 部门与人员关联信息
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/14
     */
    Integer batchUpdate(@Param("list") List<DepartmentMemberRelationship> list);
}