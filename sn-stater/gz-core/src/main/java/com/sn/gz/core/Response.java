/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Response
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 *  数据返回封装

 * @since 1.0.0
 * @author luffy
 * Date: 2018/3/21 20:47
 */
@Getter
@Setter
@ToString
@SuppressWarnings("unused")
public class Response<T> {

    private int code;
    private String message;
    private T data;
    private String exceptionClass;

    public T getData() {
        if (isSuccess()) {
            return data;
        }
        throw new BusinessException(code, message);
    }

    public boolean isSuccess() {
        return this.code == StatusCode.SUCCESS.getCode();
    }

    public boolean isEmpty() {
        return Objects.isNull(data);
    }

    public boolean isNotEmpty() {
        return Objects.nonNull(data);
    }

}
