/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RedisDao
 * Author:   xiaoxueliang
 * Date:     2018/3/26
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.sn.gz.redis.starter.AbstractRedisDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis操作类
 *
 * @author xiaoxueliang
 * create 2018/3/26
 */
@Component
public class OrgRedisDAO extends AbstractRedisDao {
    public OrgRedisDAO(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

}
