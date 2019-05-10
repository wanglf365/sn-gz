/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: UnionUserUpdateInDTO
 * Author:   lufeiwang
 * Date:   2019/4/26
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;

/**
 * 全局用户账户
 *
 * @author lufeiwang
 * 2019/4/26
 */
@Data
public class UnionUserUpdateInDTO implements Serializable {
    /**
     * 密码
     */
    private String password;
    /**
     * 手机
     */
    private String phone;
}
