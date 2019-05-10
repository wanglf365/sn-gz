/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: RoleUpdateInDTO
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色修改入参
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Data
public class RoleUpdateInDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色组id
     */
    private Long roleTeamId;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 职能描述
     */
    private String description;
}
