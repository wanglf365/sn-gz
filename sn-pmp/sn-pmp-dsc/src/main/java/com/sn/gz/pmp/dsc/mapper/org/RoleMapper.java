/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RoleMapper
 * Author:   xiaoxueliang
 * Date:     2018/3/22
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.Authority;
import com.sn.gz.pmp.dsc.entity.org.Role;
import com.sn.gz.pmp.dsc.model.org.RoleMemberOutModel;
import com.sn.gz.pmp.dsc.model.org.RoleOutModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色
 *
 * @author lufeiwang
 * 2019/4/25
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色所有权限详细信息
     *
     * @param roleList 角色集合
     * @return java.util.List
     * @author xiaoxueliang
     * 2018/4/14
     */
    List<Authority> listAuthoritiesByRoleIds(@Param("roleList") List<Role> roleList);

    /**
     * 角色配置-获取角色成员
     *
     * @param roleId 角色id
     * @return java.util.List
     * @author Enma.ai
     * 2018/8/23
     */
    List<RoleMemberOutModel> listRoleMember(@Param("roleId") Long roleId);

    /**
     * 按关键字查询角色列表
     *
     * @param groupId 集团id
     * @param keyword 关键字
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.RoleOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    List<RoleOutModel> listRole(@Param("groupId") Long groupId, @Param("keyword") String keyword);
}
