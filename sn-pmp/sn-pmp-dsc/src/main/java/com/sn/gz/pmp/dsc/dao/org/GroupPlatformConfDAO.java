/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DepartmentDAO
 * Author:   xiaoxueliang
 * Date:     2018-07-11
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.org.Department;
import com.sn.gz.pmp.dsc.entity.org.GroupPlatformConf;
import com.sn.gz.pmp.dsc.mapper.org.GroupPlatformConfMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台配置
 *
 * @author xiaoxueliang
 * 2018-07-11
 */
@Component
public class GroupPlatformConfDAO extends BaseDAO<GroupPlatformConf, Long> {

    @Resource
    private GroupPlatformConfMapper groupPlatformConfMapper;

    @Override
    public BaseMapper<GroupPlatformConf> getMapper() {
        return groupPlatformConfMapper;
    }

    /**
     * 根据集团id查询
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.GroupPlatformConf>
     * @author lufeiwang
     * 2019/5/6
     */
    public List<GroupPlatformConf> listByGroupId(Long groupId) {
        EntityClassWrapper<GroupPlatformConf> ew = new EntityClassWrapper(Department.class);
        ew.eq("groupId", groupId);
        return groupPlatformConfMapper.selectList(ew);
    }
}
