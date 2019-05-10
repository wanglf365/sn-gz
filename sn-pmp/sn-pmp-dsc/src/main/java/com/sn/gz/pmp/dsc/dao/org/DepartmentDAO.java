/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DepartmentDAO
 * Author:   xiaoxueliang
 * Date:     2018-07-11
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.org.Department;
import com.sn.gz.pmp.dsc.mapper.org.DepartmentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门表DAO
 *
 * @author xiaoxueliang
 * 2018-07-11
 */
@Component
public class DepartmentDAO extends BaseDAO<Department, Long> {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public BaseMapper<Department> getMapper() {
        return departmentMapper;
    }

    /**
     * 根据集团id查询所有部门，包括所有层级
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/4/25
     */
    public List<Department> listByGroupId(Long groupId) {
        EntityClassWrapper<Department> ew = new EntityClassWrapper(Department.class);
        ew.eq("groupId", groupId);
        return departmentMapper.selectList(ew);
    }

    /**
     * 根据集团id删除所有部门,除了根部门
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/4/25
     */
    public Integer delete(Long groupId) {
        EntityClassWrapper<Department> ew = new EntityClassWrapper(Department.class);
        ew.eq("groupId", groupId).isNotNull("parentId");
        return departmentMapper.delete(ew);
    }

    /**
     * 批量更新部门
     *
     * @param departmentList 待更新的子孙部门
     * @return java.lang.Integer
     * @author Enma.ai
     * 2018/8/21
     */
    @SuppressWarnings("UnusedReturnValue")
    public Integer batchUpdate(List<Department> departmentList) {
        if (ListUtils.isNotNull(departmentList)) {
            return departmentMapper.batchUpdate(departmentList);
        } else {
            return 0;
        }
    }

    /**
     * 批量新增部门
     *
     * @param departmentList 部门
     * @author Enma.ai
     * 2018/8/29
     */
    public void batchInsert(List<Department> departmentList) {
        if (ListUtils.isNotNull(departmentList)) {
            departmentMapper.batchInsert(departmentList);
        }
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
    public List<Department> listByName(Long groupId, String keyword) {
        return departmentMapper.listByName(groupId, keyword);
    }
}
