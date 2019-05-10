package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

/**
 * 组织架构-权限
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Data
@ToString(callSuper = true)
@TableName("t_org_authority")
public class Authority extends BaseEntity {
    /**
     * 权限名称
     */
    @TableField("name")
    private String name;
    /**
     * 权限类目
     */
    @TableField("category")
    private String category;
    /**
     * 权限值
     */
    @TableField("value")
    private String value;
    /**
     * 权限id（对应权限字典表主键id）
     */
    @TableField("auth_id")
    private Long authId;
    /**
     * 前置权限id（如：有修改权限则一定有查看权限）（对应权限字典表主键id）
     */
    @TableField("parent")
    private Long parent;
    /**
     * 权限类型
     */
    @TableField("permission_type")
    private String permissionType;
}
