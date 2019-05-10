/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: AbstractRedisDao
 * Author:   luffy
 * Date:     2018/3/21
 */
package com.sn.gz.redis.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * BaseRedisDAO
 *
 * @author luffy
 * 2018/4/18
 */
@SuppressWarnings("unused")
public abstract class AbstractRedisDao {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public AbstractRedisDao(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected void saveValue(String key, String value, int time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    protected void setWithExpireHours(String key, String obj, int hours) {
        redisTemplate.opsForValue().set(key, obj, hours, TimeUnit.HOURS);
    }

    protected void setWithExpireMinutes(String key, String obj, int minutes) {
        redisTemplate.opsForValue().set(key, obj, minutes, TimeUnit.MINUTES);
    }

    protected void setWithExpireSeconds(String key, String obj, int seconds) {
        redisTemplate.opsForValue().set(key, obj, seconds, TimeUnit.SECONDS);
    }

    protected void setWithExpireDays(String key, String obj, int days) {
        redisTemplate.opsForValue().set(key, obj, days, TimeUnit.DAYS);
    }

    protected Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    protected void set(String key, String obj) {
        redisTemplate.opsForValue().set(key, obj);
    }

    protected void releaseCoon() {
        RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
    }

    /**
     * 将Hash存入缓存（永久）
     *
     * @param key 键
     * @param map 值
     * @author Enma.ai
     * 2018/8/18
     */
    public void saveHash(String key, Map<String, String> map) {
        saveHash(key, map, null, null);
    }

    /**
     * 将Hash存入缓存并设置有效时间
     *
     * @param key      键
     * @param map      值
     * @param time     有效时间
     * @param timeType 时间类型
     * @author Enma.ai
     * 2018/8/18
     */
    public void saveHash(String key, Map<String, String> map, Integer time, TimeUnit timeType) {
        HashOperations<String, String, Object> ops = redisTemplate.opsForHash();
        ops.putAll(key, map);
        if (null != time && time > 0 && null != timeType) {
            expire(key, time, timeType);
        }
    }

    /**
     * 获取hash中指定key的值
     *
     * @param hashKey hashKey
     * @param key     key
     * @return value
     */
    public String hashGetKey(String hashKey, String key) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.get(hashKey, key);
    }

    /**
     * 获取Hash的所有key
     *
     * @param key hashKey
     * @return java.util.List&lt;java.lang.String&gt;
     * @author xiaoxueliang
     * 2018/7/2
     */
    public List<String> getHashKeys(String key) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        Set<String> keySet = ops.keys(key);
        List<String> keyList = new ArrayList<>();
        if (null != keySet) {
            keyList.addAll(keySet);
        }
        return keyList;
    }

    /**
     * 将k\v存入redis（永久）
     *
     * @param key   key
     * @param value value
     * @author Enma.ai
     * Date: 2018/3/27
     */
    public void saveKeyValue(String key, String value) {
        saveKeyValue(key, value, null, null);
    }

    /**
     * 将k\v存入redis并设置有效时间
     *
     * @param key   key
     * @param value value
     * @author Enma.ai
     * Date: 2018/3/27
     */
    public void saveKeyValue(String key, String value, Integer time, TimeUnit timeType) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        if (null != time && time > 0 && null != timeType) {
            expire(key, time, timeType);
        }
    }

    /**
     * 根据key获取value
     *
     * @param key key
     * @return java.lang.String
     * @author Enma.ai
     * 2018/6/4
     */
    public String getValueByKey(String key) {
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        return (String) value.get(key);
    }

    /**
     * 判断指定key是否存在
     *
     * @param key key
     * @return boolean
     * @author Enma.ai
     * 2018/6/4
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key key
     * @author Enma.ai
     * 2018/6/4
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 按正则匹配进行删除
     *
     * @param pattern 正则
     * @author xiaoxueliang
     * 2018/6/29
     */
    public void deleteByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 刷新缓存有效时间
     *
     * @param key      键
     * @param time     刷新时间
     * @param timeType 时间类型
     * @return boolean 是否成功
     * @author Enma.ai
     * 2018/8/16
     */
    public void expire(String key, int time, TimeUnit timeType) {
        if (time > 0) {
            redisTemplate.expire(key, time, timeType);
        }
    }

    /**
     * 从hash表中一次获取多个key的值
     *
     * @param hashKey HashKey
     * @param keys    key集合
     * @return java.util.List
     * @author xiaoxueliang
     * 2018/6/5
     */
    public List<String> hashMultiGet(String hashKey, List<String> keys) {
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        return ops.multiGet(hashKey, keys);
    }
}
