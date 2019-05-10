package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色表
 *
 * @author xiaoxueliang
 * @since 2018-03-21
 */
@Data
@ToString(callSuper = true)
@TableName("t_org_role")
public class Role extends BaseEntity {
    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 角色组id
     */
    @TableField(value = "role_team_id")
    private Long roleTeamId;

    /**
     * 职能描述
     */
    @TableField("description")
    private String description;
    /**
     * 是否启用（1：启用，0：不启用）
     */
    @TableField("enable")
    private String enable;
    /**
     * admin（自动创建的管理员角色不可删除）
     */
    @TableField("admin")
    private String admin;
    /**
     * 数据权限：本部门（1/0）
     */
    @TableField("this_department")
    private String thisDepartment;
    /**
     * 数据权限：子部门（1/0）
     */
    @TableField("sub_department")
    private String subDepartment;
}
