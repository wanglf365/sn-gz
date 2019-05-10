/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: HttpMessageConvertsConfiguration
 * Author:   lufeiwang
 * Date:     2018/3/31 16:55
 *
 * @since 1.0.0
 */
package com.sn.gz.websupport.web.help;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * 返回结果转换器,替换成fastjson做json转换,缺省是jackjson
 * @author lufeiwang
 * Date 2018/3/31
 * @since 1.0.0
 */
@Configuration
@AutoConfigureAfter({JacksonAutoConfiguration.class})
public class HttpMessageConvertsConfiguration {
    @Configuration
    @ConditionalOnClass({JSON.class, FastJsonHttpMessageConverter4.class})
    static class FastJsonHttpMessageConvertersConfiguration {

        @Bean
        @ConditionalOnClass(FastJsonHttpMessageConverter4.class)
        @Primary
        public FastJsonHttpMessageConverter4 fastJsonHttpMessageConverter() {

            FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
            fastConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setSerializerFeatures(
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.DisableCircularReferenceDetect);
            fastJsonConfig.setSerializeFilters(new BigDecimalValueFilter());
            fastConverter.setFastJsonConfig(fastJsonConfig);
            return fastConverter;
        }
    }

}
