/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RoleAuthorityDAO
 * Author:   Enma.ai
 * Date:     2019-01-02
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.org.MemberRoleRelationship;
import com.sn.gz.pmp.dsc.entity.org.RoleAuthorityRelationship;
import com.sn.gz.pmp.dsc.mapper.org.RoleAuthorityRelationshipMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色权限关联表
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Component
public class RoleAuthorityRelationshipDAO extends BaseDAO<RoleAuthorityRelationship, Long> {

    @Resource
    private RoleAuthorityRelationshipMapper roleAuthorityRelationshipMapper;

    @Override
    public BaseMapper<RoleAuthorityRelationship> getMapper() {
        return roleAuthorityRelationshipMapper;
    }

    /**
     * 批量保存
     *
     * @param roleAuthorityList 角色权限关联集合
     * @author xiaoxueliang
     * 2018/8/6
     */
    public void batchSave(List<RoleAuthorityRelationship> roleAuthorityList) {
        if (ListUtils.isNotNull(roleAuthorityList)) {
            roleAuthorityRelationshipMapper.batchInsert(roleAuthorityList);
        }
    }


    /**
     * 根据角色id和集团id删除
     *
     * @param groupId 集团id
     * @param roleId  角色id
     */
    public void deleteByRoleId(Long groupId, Long roleId) {
        EntityClassWrapper<RoleAuthorityRelationship> ew = new EntityClassWrapper(RoleAuthorityRelationship.class);
        ew.eq("groupId", groupId).eq("roleId", roleId);
        roleAuthorityRelationshipMapper.delete(ew);
    }
}
