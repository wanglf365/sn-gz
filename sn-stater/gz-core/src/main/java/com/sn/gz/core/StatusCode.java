/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: StatusCode
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 错误码定义
 * @since 1.0.0
 * @author luffy
 * Date: 2018/3/21 20:43
 */
@Getter
@ToString
@AllArgsConstructor
public enum StatusCode {

    /**
     * 成功
     */
    SUCCESS(200, "message.success", "操作成功"),
    INVALID_TENANT_ID(1000, "message.error.sandbox.empty", "sandbox is empty or invalid!"),
    SESSION_EXPIRED(302, "message.error.session.expired", "登录超时!"),
    PARAM_ILLEGAL(400, "message.error.parameter.illegal", "非法入参!"),
    DUPLICATE_LOGIN(401, "message.error.forbidden", "重复登录!"),
    FORBIDDEN(403, "message.error.forbidden", "没有操作权限!"),
    SERVER_EXCEPTION(500, "message.error.server.failure", "系统错误!"),
    BUSINESS_EXCEPTION(525, "", "BusinessException"),
    OPEN_ID_NOT_CONFIG(535, "", "OpenIdNotConfig"),
    BUSINESS_WARNING(545, "", "BusinessException"),
    NOT_EXIST(-1, "message.error.object.missing", "object not found!"),;

    /**
     * 异常自定义码值
     */
    private final int code;
    /**
     * 错误消息编码,用于替换messages.properties的提示语
     */
    private final String messageCode;
    /**
     * 未找到替换提示语时的默认消息
     */
    private final String message;
}
