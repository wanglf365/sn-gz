/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RedisAspect
 * Author:   lufeiwang
 * Date:   2018/8/6
 */
package com.sn.gz.redis.starter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redis切面，统一处理链接释放
 *
 * @author lufeiwang
 * 2018/8/6
 */
@Aspect
@Component
@Slf4j
@ConditionalOnClass(JoinPoint.class)
public class RedisAspect {

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Pointcut("execution(public * com.sn..*.dao.redis.*.*(..))")
    public void releaseCoon() {
    }

    @After("releaseCoon()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("=======redis release connection======");
        //TODO 需要考虑上下文已有事务管理时，不能强制释放连接
        RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
    }

}
