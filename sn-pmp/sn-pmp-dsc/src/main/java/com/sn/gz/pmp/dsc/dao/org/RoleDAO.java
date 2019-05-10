/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RoleDao
 * Author:   xiaoxueliang
 * Date:     2018/3/26
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.pmp.dsc.entity.org.Authority;
import com.sn.gz.pmp.dsc.entity.org.Role;
import com.sn.gz.pmp.dsc.mapper.org.RoleMapper;
import com.sn.gz.pmp.dsc.model.org.RoleMemberOutModel;
import com.sn.gz.pmp.dsc.model.org.RoleOutModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 *
 * @author xiaoxueliang
 * 2018/3/26
 */
@Component
public class RoleDAO extends BaseDAO<Role, Long> {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public BaseMapper<Role> getMapper() {
        return roleMapper;
    }

    /**
     * 按条件更新角色表信息
     *
     * @param role          角色表待更新数据
     * @param entityWrapper 条件
     * @return int
     * @author xiaoxueliang
     * 2018-04-20
     */
    public int update(Role role, EntityWrapper<Role> entityWrapper) {
        return roleMapper.update(role, entityWrapper);
    }

    /**
     * 获取角色所有权限详细信息
     *
     * @param roleList 角色列表
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Authority>
     * @author lufeiwang
     * 2019/4/25
     */
    public List<Authority> findRolesAuthoritiesInfo(List<Role> roleList) {
        return roleMapper.listAuthoritiesByRoleIds(roleList);
    }

    /**
     * 角色配置-获取角色成员
     *
     * @param roleId 角色id
     * @return java.util.List
     * @author Enma.ai
     * 2018/8/23
     */
    public List<RoleMemberOutModel> listRoleMember(Long roleId) {
        return roleMapper.listRoleMember(roleId);
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
    public List<RoleOutModel> listRole(Long groupId, String keyword) {
        return roleMapper.listRole(groupId, keyword);
    }

}
