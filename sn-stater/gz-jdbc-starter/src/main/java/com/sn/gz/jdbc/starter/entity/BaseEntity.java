/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BaseEntity
 * Author:   luffy
 * Date:     2018/3/21 18:44
 *
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 数据库基本实体类，包含id，创建时间，修改时间
 *
 * @author luffy
 * Date 2018/3/21
 * @since 1.0.0
 */
@SuppressWarnings("unused")
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEntity extends BaseEmptyEntity {
    /**
     * ID
     */
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 集团ID
     */
    @TableField(value = "group_id", fill = FieldFill.DEFAULT)
    private Long groupId;
    /**
     * 创建时间
     */
    @TableField(value = "create_datetime", fill = FieldFill.INSERT)
    private Date createDatetime;
    /**
     * 修改时间
     */
    @TableField(value = "modified_datetime", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedDatetime;

}
