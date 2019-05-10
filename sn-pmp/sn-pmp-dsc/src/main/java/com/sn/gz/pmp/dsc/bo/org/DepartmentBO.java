/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DepartmentBO
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.dsc.bo.org;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 部门
 * 树形存储结构
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Data
public class DepartmentBO {

    /**
     * ID
     */
    private Long id;
    /**
     * 集团ID
     */
    private Long groupId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 部门描述
     */
    private String description;
    /**
     * id路径，使用逗号分隔，包含自身
     */
    private String path;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 上级部门,根部门parentId=null
     */
    private Long parentId;

    /**
     * 部门层级
     * 默认公司为第一层级，
     * 注册时会默认创建l额level=0
     * 依次+1
     */
    private Integer level;

    /**
     * 成员列表
     */
    private List<MemberRoleBO> memberList;

    /**
     * 儿子部门
     */
    private List<DepartmentBO> children;

}
