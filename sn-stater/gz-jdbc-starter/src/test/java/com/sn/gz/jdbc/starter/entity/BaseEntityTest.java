/*
 * Copyright (C), 2018-2019, 深圳拾年数据技术有限公司
 * FileName: BaseEntityTest
 * Author:   hanson
 * Date:   2019/4/22
 */
package com.sn.gz.jdbc.starter.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hanson
 * 2018/6/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BaseEntityTest extends BaseEntity{
    private static volatile ConcurrentHashMap<String,String> dbFieldMap = null;


    @TableField(value = "name", fill = FieldFill.INSERT)
    private String testName;

}


