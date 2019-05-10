/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RoleDao
 * Author:   xiaoxueliang
 * Date:     2018/3/26
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.entity.org.RoleTeam;
import com.sn.gz.pmp.dsc.mapper.org.RoleTeamMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色组管理dao
 *
 * @author xiaoxueliang
 * 2018/3/26
 */
@Component
public class RoleTeamDAO extends BaseDAO<RoleTeam, Long> {

    @Resource
    private RoleTeamMapper roleTeamMapper;

    @Override
    public BaseMapper<RoleTeam> getMapper() {
        return roleTeamMapper;
    }

    /**
     * 获取全部角色组
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.RoleTeam>
     * @author lufeiwang
     * 2019/4/29
     */
    public List<RoleTeam> listByGroupId(Long groupId) {
        EntityClassWrapper<RoleTeam> ew = new EntityClassWrapper(RoleTeam.class);
        ew.eq("groupId", groupId);
        return roleTeamMapper.selectList(ew);
    }
}
