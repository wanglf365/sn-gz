/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DubboConfig
 * Author:   xiaox
 * Date:     2018/3/20
 */
package com.sn.gz.pmp.dsc.config;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * dubbo配置类
 *
 * @author lufeiwang
 * 2019/4/12
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.sn.gz.pmp.dsc.service")
@PropertySource("classpath:/dubbo/dubbo.properties")
@DubboComponentScan(basePackages = "com.sn.gz.pmp.dsc.service")
public class DubboConfig {
}
