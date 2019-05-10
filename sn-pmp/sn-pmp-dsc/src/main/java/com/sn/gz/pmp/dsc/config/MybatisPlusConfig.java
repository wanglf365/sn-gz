/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: MybatisPlusConfig
 * Author:   luffy
 * Date:   2018/4/24 14:40
 * Since: 1.0.0
 */
package com.sn.gz.pmp.dsc.config;

import com.sn.gz.jdbc.starter.config.MybatisPlusConfigBase;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * mybatis配置
 *
 * @author luffy
 * @since 1.0.0
 * 2018/4/24
 */
@Configurable
@MapperScan("com.sn.gz.pmp.dsc.mapper")
@Slf4j
public class MybatisPlusConfig extends MybatisPlusConfigBase {
}
