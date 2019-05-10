/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Address
 * Author:   Enma.ai
 * Date:     2019-01-23
 */
package com.sn.gz.pmp.dsc.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 地址表
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Data
@TableName("t_global_address")
public class Address {
    /**
     * ID
     */
    @TableField(value = "id")
    private Long id;
    /**
     * 地址名称
     */
    @TableField("name")
    private String name;
}
