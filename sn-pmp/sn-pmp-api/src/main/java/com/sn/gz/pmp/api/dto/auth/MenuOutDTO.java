
package com.sn.gz.pmp.api.dto.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单dto
 *
 * @author xiaoxueliang
 * 2018/4/17
 */
@Data
public class MenuOutDTO implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 权限集合（使用逗号分隔）
     */
    private String authority;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 键（与前端对应）
     */
    private String mapKey;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 是否叶子节点（0：否、1：是）
     */
    private String leaf;
    /**
     * 父节点路径，使用逗号分隔
     */
    private String path;
    /**
     * 是否可见
     */
    private Boolean display;
    /**
     * 是否放行（0：否、1：是）
     */
    private String accessAllow;
    /**
     * 孩子节点
     */
    private List<MenuOutDTO> children = new ArrayList<>();

    /**
     * 小图标key
     */
    private String iconKey;
    /**
     * menu 类型 1.GROUP(集团权限)2.PUBLIC(集团项目公共权限)3.PROJECT(项目权限)
     */
    private String menuType;
}
