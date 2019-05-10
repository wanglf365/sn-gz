/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: GroupDao
 * Author:   Enma.ai
 * Date:     2018/3/28
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.pmp.dsc.entity.org.Group;
import com.sn.gz.pmp.dsc.mapper.org.GroupMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * GroupDao
 *
 * @author Enma.ai
 * 2018/3/28
 */
@Component
public class GroupDAO extends BaseDAO<Group, Long> {

    @Resource
    private GroupMapper groupMapper;

    @Override
    public BaseMapper<Group> getMapper() {
        return groupMapper;
    }
}
