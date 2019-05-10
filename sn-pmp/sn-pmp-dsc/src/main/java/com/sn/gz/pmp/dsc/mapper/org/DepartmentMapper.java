/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DepartmentMapper
 * Author:   xiaoxueliang
 * Date:     2018-07-11
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门表Mapper
 *
 * @author xiaoxueliang
 * 2018-07-11
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 批量更新部门
     *
     * @param departmentList 待更新的部门
     * @return java.lang.Integer
     * @author Enma.ai
     * 2018/8/21
     */
    Integer batchUpdate(@Param("list") List<Department> departmentList);


    /**
     * 批量新增部门
     *
     * @param departmentList 部门集合
     * @author Enma.ai
     * 2018/8/29
     */
    void batchInsert(@Param("list") List<Department> departmentList);

    /**
     * 按名字模糊查询
     *
     * @param groupId 集团id
     * @param keyword 关键字
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/5/6
     */
    List<Department> listByName(@Param("groupId") Long groupId, @Param("keyword") String keyword);
}