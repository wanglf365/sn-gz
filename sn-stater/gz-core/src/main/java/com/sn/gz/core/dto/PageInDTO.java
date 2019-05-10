/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: PageInDTO
 * Author:   xiaoxueliang
 * Date:     2018/4/13
 */
package com.sn.gz.core.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页相关字段
 *
 * @author xiaoxueliang
 * 2018/4/13
 */
@Data
public class PageInDTO implements Serializable {

    /**
     * 第几页
     */
    private Integer current;
    /**
     * 每页条数
     */
    private Integer size;

    public PageInDTO() {
        current = 1;
        size = 10;
    }
}
