/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: UserDao
 * Author:   Enma.ai
 * Date:     2018/3/23
 */
package com.sn.gz.pmp.dsc.dao.auth;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.mapper.auth.UnionUserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户Dao
 *
 * @author Enma.ai
 * 2018/3/23
 */
@Component
public class UnionUserDAO extends BaseDAO<UnionUser, Long> {

    @Resource
    private UnionUserMapper unionUserMapper;

    @Override
    public BaseMapper<UnionUser> getMapper() {
        return unionUserMapper;
    }

    /**
     * 批量保存用户
     *
     * @param userList 用户信息集合
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/5/4
     */
    public Integer batchInsert(List<UnionUser> userList) {
        if (ListUtils.isNull(userList)) {
            return 0;
        }
        return unionUserMapper.batchInsert(userList);
    }
}
