/*
 * Copyright (C), 2018-2019, 深圳拾年技术有限公司
 * FileName: UserLoginInDTO
 * Author:   lufeiwang
 * Date:   2019/5/8
 */
package com.sn.gz.pmp.api.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录
 *
 * @author lufeiwang
 * 2019/5/8
 */
@Data
public class UserLoginInDTO implements Serializable{

    /**
     * 手机号码
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 终端类型:pc、ios、android
     */
    private String terminalType;
    /**
     * 客户填写的图片验证码
     */
    private String customerGraphCode;
    /**
     * 图片验证码key
     */
    private String key;
}
