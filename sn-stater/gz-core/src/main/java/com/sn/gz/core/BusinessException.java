/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BusinessException
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.core;

import lombok.Getter;
import lombok.ToString;
import org.springframework.core.NestedRuntimeException;

/**
 * 业务异常类
 * @author luffy
 * Date 2018/3/21
 * @since 1.0.0
 */
@Getter
@ToString
public class BusinessException extends NestedRuntimeException {

    private int code;
    /**
     * 错误信息替换编码
     */
    private String message;

    public BusinessException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.code = statusCode.getCode();
    }


    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.code = StatusCode.BUSINESS_EXCEPTION.getCode();
    }


    public BusinessException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
