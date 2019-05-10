/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: LoginUser
 * Author:   Enma.ai
 * Date:     2018/3/26
 */
package com.sn.gz.core.jwt;

import lombok.Data;

import java.util.Date;

/**
 * 存入Token的用户信息
 *
 * @author Enma.ai
 * 2018/3/26
 */
@Data
public class LoginUser {

    /**
     * unionId
     */
    private Long unionId;
    /**
     * userID = customerId
     */
    private Long memberId;
    /**
     * 集团id
     */
    private Long groupId;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 终端类型
     */
    private String terminalType;
    /**
     * 手机
     */
    private String phone;

}
