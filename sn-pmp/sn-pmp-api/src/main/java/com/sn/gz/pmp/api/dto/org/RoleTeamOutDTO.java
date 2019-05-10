/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: RoleTeamOutDTO
 * Author:   lufeiwang
 * Date:   2019/4/29
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色组
 *
 * @author lufeiwang
 * 2019/4/29
 */
@Data
public class RoleTeamOutDTO implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
}
