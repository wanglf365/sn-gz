package com.sn.gz.pmp.dsc.entity.auth;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

/**
 * 菜单权限表
 *
 * @author xiaoxueliang
 * @since 2018-04-17
 */
@Data
@TableName("t_org_menu")
public class Menu {

    public static final String SEPARATE = ",";
    /**
     * ID
     */
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;
    /**
     * 权限集合（使用逗号分隔）
     */
    @TableField("authority")
    private String authority;
    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 键（与前端对应）
     */
    @TableField("map_key")
    private String mapKey;
    /**
     * 父id
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 是否叶子节点（0：否、1：是）
     */
    @TableField("leaf")
    private String leaf;
    /**
     * 父节点路径，使用逗号分隔
     */
    @TableField("path")
    private String path;
    /**
     * 是否放行（0：否、1：是）
     */
    @TableField("access_allow")
    private String accessAllow;
    /**
     * 小图标key
     */
    @TableField("icon_key")
    private String iconKey;
    /**
     * 权限类型
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * 是否显示（0：否、1：是）
     */
    @TableField("display")
    private String display;
}
