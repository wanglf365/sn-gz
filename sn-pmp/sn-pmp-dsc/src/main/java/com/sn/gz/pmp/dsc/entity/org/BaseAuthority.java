/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BaseAuthority
 * Author:   Enma.ai
 * Date:     2019-01-02
 */
package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

/**
 * 权限字典表
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Data
@TableName("t_org_base_authority")
public class BaseAuthority {

    public BaseAuthority() {
    }

    public BaseAuthority(Long id, String name, String value, String category, Long parent, String permissionType) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.category = category;
        this.parent = parent;
        this.permissionType = permissionType;
    }

    /**
     * ID
     */
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 权限名称
     */
    @TableField("name")
    private String name;
    /**
     * 权限值
     */
    @TableField("value")
    private String value;
    /**
     * 权限类别
     */
    @TableField("category")
    private String category;
    /**
     * 权限类型:1.GROUP(集团权限);2.PUBLIC(集团项目公共权限)3.PROJECT(项目权限)
     */
    @TableField("permission_type")
    private String permissionType;
    /**
     * 前置权限id（如：有修改权限则一定有查看权限）
     */
    @TableField("parent")
    private Long parent;
}
