/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: ApplicationBoot
 * Author:   luffy
 * Date:   2018/4/24
 */
package com.sn.gz.pmp.dsc;


import com.sn.gz.pmp.dsc.config.DubboConfig;
import com.sn.gz.pmp.dsc.config.MybatisPlusConfig;
import com.sn.gz.redis.starter.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 程序入口
 *
 * @author luffy
 * 2018/4/24
 */
@SpringBootApplication
@EnableTransactionManagement
@Import({MybatisPlusConfig.class, DubboConfig.class,RedisConfig.class})
@EnableAutoConfiguration
@ComponentScan(value = {"com.sn.gz"})
@Slf4j
public class PmpBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(PmpBootstrap.class, args);
    }
}