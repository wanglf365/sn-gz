/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DepartmentCustomerDAO
 * @since 1.0.0
 * Author:   xiaoxueliang
 * Date:     2018-07-11
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.jdbc.starter.query.Filter;
import com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship;
import com.sn.gz.pmp.dsc.mapper.org.DepartmentMemberRelationshipMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户所在部门关系表DAO
 *
 * @author xiaoxueliang
 * 2018-07-11
 */
@Component
public class DepartmentMemberRelationshipDAO extends BaseDAO<DepartmentMemberRelationship, Long> {

    @Resource
    private DepartmentMemberRelationshipMapper departmentMemberRelationshipMapper;

    @Override
    public BaseMapper<DepartmentMemberRelationship> getMapper() {
        return departmentMemberRelationshipMapper;
    }

    /**
     * 根据集团id删除所有部门和成员关系
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/4/25
     */
    public Integer delete(Long groupId) {
        EntityClassWrapper<DepartmentMemberRelationship> ew = new EntityClassWrapper(DepartmentMemberRelationship.class);
        ew.eq("groupId", groupId);
        return departmentMemberRelationshipMapper.delete(ew);
    }

    /**
     * 批量保存部门与人员的关联关系
     *
     * @param departmentCustomerList 部门与人员关联信息
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/7/14
     */
    public Integer batchInsert(List<DepartmentMemberRelationship> departmentCustomerList) {
        if (ListUtils.isNotNull(departmentCustomerList)) {
            return departmentMemberRelationshipMapper.batchInsert(departmentCustomerList);
        } else {
            return 0;
        }
    }

    /**
     * 根据集团id查询所有成员和部门关系
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship>
     * @author lufeiwang
     * 2019/4/26
     */
    public List<DepartmentMemberRelationship> listByGroupId(Long groupId) {
        EntityClassWrapper<DepartmentMemberRelationship> ew = new EntityClassWrapper(DepartmentMemberRelationship.class);
        ew.eq("groupId", groupId);
        return departmentMemberRelationshipMapper.selectList(ew);
    }

    /**
     * 更改成员的主部门为制定部门
     *
     * @param groupId      集团id
     * @param memberId     成员id
     * @param departmentId 部门id
     * @author lufeiwang
     * 2019/5/5
     */
    public void updateMainDepartment(Long groupId, Long memberId, Long departmentId) {
        Date date = new Date();
        List<Filter> filterList = Filter.build()
                .add(Filter.eq("groupId", groupId))
                .add(Filter.eq("memberId", memberId))
                .build();

        DepartmentMemberRelationship updateEntity = new DepartmentMemberRelationship();
        updateEntity.setMainDepartment(OrgConstants.DepartmentIsMain.NOT_MAIN);
        updateEntity.setModifiedDatetime(date);
        update(updateEntity, filterList);

        List<Filter> mainFilterList = Filter.build()
                .add(Filter.eq("groupId", groupId))
                .add(Filter.eq("memberId", memberId))
                .add(Filter.eq("departmentId", departmentId))
                .build();

        DepartmentMemberRelationship mainUpdateEntity = new DepartmentMemberRelationship();
        mainUpdateEntity.setMainDepartment(OrgConstants.DepartmentIsMain.IS_MAIN);
        mainUpdateEntity.setModifiedDatetime(date);
        update(mainUpdateEntity, mainFilterList);
    }


}
