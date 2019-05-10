/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DataSourceConfig
 * Author:   lufeiwang
 * Date:   2019/4/10
 */
package com.sn.gz.jdbc.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.sn.gz.core.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据源
 *
 * @author lufeiwang
 * 2019/4/10
 */
@Component
@Slf4j
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    private static final String DB_PREFIX = "spring.datasource";

    private static final String SECRET_KEY = "SN190418";

    @Bean("druidDataSource")
    public DataSource druidDataSource() {
        Binder binder = Binder.get(environment);
        DataResourceProperties dataResourceProperties = binder.bind(DB_PREFIX, Bindable.of(DataResourceProperties.class)).orElse(null);

        log.info("目标数据库{}>>>>>>>>>>>>>", dataResourceProperties.getUrl());
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dataResourceProperties.getUrl());
        datasource.setDriverClassName(dataResourceProperties.getDriverClassName());
        datasource.setUsername(dataResourceProperties.getUsername());
        datasource.setPassword(decryptPassword(dataResourceProperties.getPassword()));
        datasource.setInitialSize(Integer.valueOf(dataResourceProperties.getInitialSize()));
        datasource.setMinIdle(Integer.valueOf(dataResourceProperties.getMinIdle()));
        datasource.setMaxWait(Long.valueOf(dataResourceProperties.getMaxWait()));
        datasource.setMaxActive(Integer.valueOf(dataResourceProperties.getMaxActive()));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(dataResourceProperties.getMinEvictableIdleTimeMillis()));
        try {
            datasource.setFilters(dataResourceProperties.getFilters());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }

    /**
     * 密码解密成明文
     *
     * @param cipher 密文
     * @return 明文
     */
    private String decryptPassword(String cipher) {
        return EncryptUtils.decrypt(cipher, SECRET_KEY);
    }

}
