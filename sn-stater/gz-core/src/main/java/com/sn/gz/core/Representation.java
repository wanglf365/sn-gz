/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Representation
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.core;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 接口返回的基础类
 *
 * @author luffy
 * Date: 2018/3/21 20:48
 * @since 1.0.0
 */
@Getter
@ToString
@SuppressWarnings("unused")
public class Representation<T> implements Serializable {

    private int code = StatusCode.SUCCESS.getCode();
    private String message;
    private T data;

    public Representation() {
        this(StatusCode.SUCCESS);
    }

    public Representation(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    public Representation(StatusCode statusCode, String desc) {
        this.code = statusCode.getCode();
        this.message = desc;
    }

    public Representation(int code, String desc) {
        this.code = code;
        this.message = desc;
    }

    public Representation(Throwable e) {
        this(StatusCode.SERVER_EXCEPTION);
        this.message = e.getMessage();
    }

    public Representation(T data) {
        this(StatusCode.SUCCESS);
        this.data = data;
    }

    public static Representation success() {
        return new Representation(StatusCode.SUCCESS);
    }
}
