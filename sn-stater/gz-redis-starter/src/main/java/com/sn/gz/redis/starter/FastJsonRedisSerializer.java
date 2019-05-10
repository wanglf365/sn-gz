/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: SwaggerConfigurer
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.redis.starter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 *  fastjson自定义序列化机器
 * @since 1.0.0
 * @author luffy
 * Date: 2018/4/18 19:26
 */
@SuppressWarnings("unused")
public class FastJsonRedisSerializer implements RedisSerializer<Object> {

    private static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return JSON.parse(bytes, Feature.DisableCircularReferenceDetect);
        } catch (Exception ex) {
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        return JSON
                .toJSONBytes(t, SerializerFeature.DisableCircularReferenceDetect,
                        SerializerFeature.WriteClassName);
    }

}
