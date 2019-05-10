/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DataResourceProperties
 * Author:   lufeiwang
 * Date:   2019/4/10
 */
package com.sn.gz.jdbc.starter.config;

import lombok.Data;

/**
 * 数据源配置项
 *
 * @author lufeiwang
 * 2019/4/10
 */
@Data
public class DataResourceProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;
    private String connectionProperties;
}
