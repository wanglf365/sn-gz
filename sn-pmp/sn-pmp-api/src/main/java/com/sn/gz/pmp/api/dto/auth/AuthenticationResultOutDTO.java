/*
 * Copyright (C), 2018-2019, 深圳拾年技术有限公司
 * FileName: AuthenticationResultOutDTO
 * Author:   lufeiwang
 * Date:   2019/5/8
 */
package com.sn.gz.pmp.api.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 鉴权返回
 *
 * @author lufeiwang
 * 2019/5/8
 */
@Data
public class AuthenticationResultOutDTO implements Serializable {
    /**
     * JWT
     */
    private String jwt;
    /**
     * 默认菜单类型:GROUP(集团)PROJECT(项目)
     */
    private String menuType;
}
