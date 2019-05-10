/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: AuthorityMapper
 * Author:   xiaoxueliang
 * Date:     2018/3/22
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.Authority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限
 *
 * @author lufeiwang
 * 2019/4/25
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

    /**
     * 批量保存集团权限表
     *
     * @param authorityList 集团权限表
     * @author xiaoxueliang
     * 2018/8/3
     */
    void batchInsert(@Param("authorityList") List<Authority> authorityList);

    /**
     * 获取用户所有权限
     *
     * @param memberId       用户id
     * @param groupId        集团id
     * @param permissionType 权限类型
     * @return 返回用户权限字符串集合
     */
    List<Authority> listMemberAuthorities(@Param("memberId") Long memberId, @Param("groupId") Long groupId, @Param("permissionType") String permissionType);

    /**
     * 找出指定角色不包含在给定id集合中的权限id集合
     *
     * @param notInIdList 排除的权限id
     * @param roleId      角色id
     * @return java.util.List
     * @author xiaoxueliang
     * 2018/3/28
     */
    List<Long> listNotInAuthorityIdsByRoleIdAndAuthIds(@Param("notInIdList") List<Long> notInIdList, @Param("roleId") Long roleId);

    /**
     * 获取当前用户的所有权限的类型判断加载菜单
     *
     * @param memberId 用户id
     * @param groupId  集团id
     * @return java.util.List<java.lang.String>
     * @author lufeiwang
     * 2019/5/8
     */
    List<String> listMemberPermissionType(@Param("memberId") Long memberId, @Param("groupId") Long groupId);

}
