/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: AuthorityDao
 * Author:   xiaoxueliang
 * Date:     2018/3/28
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.org.Authority;
import com.sn.gz.pmp.dsc.mapper.org.AuthorityMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Component
public class AuthorityDAO extends BaseDAO<Authority, Long> {

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public BaseMapper<Authority> getMapper() {
        return authorityMapper;
    }

    /**
     * 批量保存集团权限表
     *
     * @param authorityList 集团权限表
     * @author xiaoxueliang
     * 2018/8/6
     */
    public void batchInsert(List<Authority> authorityList) {
        authorityMapper.batchInsert(authorityList);
    }

    /**
     * 获取用户所有权限
     * (包含默认的，比如有编辑，必有查看)
     *
     * @param memberId 成员id
     * @return 返回用户权限字符串集合
     */
    public List<Authority> listMemberAuthorities(Long memberId, Long groupId, String permissionType) {
        return authorityMapper.listMemberAuthorities(memberId, groupId, permissionType);
    }

    /**
     * 获取集团所有权限
     *
     * @param groupId 成员id
     * @return 返回用户权限字符串集合
     */
    public List<Authority> listGroupAuthorities(Long groupId) {
        EntityClassWrapper<Authority> ew = new EntityClassWrapper(Authority.class);
        ew.eq("groupId", groupId);
        return authorityMapper.selectList(ew);
    }

    /**
     * 找出指定角色不包含在给定id集合中的权限id集合
     *
     * @param notInIdList 排除的权限id
     * @param roleId      角色id
     * @return java.util.List
     * @author xiaoxueliang
     * 2018/3/28
     */
    public List<Long> listNotInAuthorityIdsByRoleIdAndAuthIds(List<Long> notInIdList, Long roleId) {
        return authorityMapper.listNotInAuthorityIdsByRoleIdAndAuthIds(notInIdList, roleId);
    }

    /**
     * 获取当前用户的所有权限的类型判断加载菜单
     *
     * @param memberId 用户id
     * @param groupId  集团id
     * @return java.util.List<java.lang.String>
     * @author lufeiwang
     * 2019/5/8
     */
    public List<String> listMemberPermissionType(Long memberId, Long groupId) {
        return authorityMapper.listMemberPermissionType(memberId, groupId);
    }
}
