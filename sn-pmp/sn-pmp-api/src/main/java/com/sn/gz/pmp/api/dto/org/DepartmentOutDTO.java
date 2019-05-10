/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DepartmentBO
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.api.dto.org;

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
public class DepartmentOutDTO implements Serializable{

    /**
     * ID
     */
    private Long id;
    /**
     * 部门名称
     */
    private String name;


    /**
     * 儿子部门
     */
    private List<DepartmentOutDTO> children;

}
