/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: PageOutDTO
 * Author:   xiaoxueliang
 * Date:     2018/4/13 10:16
 *
 * @since 1.0.0
 */
package com.sn.gz.core.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页DTO
 *
 * @author xiaoxueliang
 * @since 1.0.0
 * 2018/4/13
 */
@Data
public class PageOutDTO<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 每页显示条数，默认 10
     */
    private Integer size;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 当前页
     */
    private Integer current;

    public PageOutDTO() {
        size = 10;
        current = 1;
    }

    public PageOutDTO(List<T> data, Integer total, Integer size, Integer pages, Integer current) {
        this.data = data;
        this.total = total;
        this.size = size;
        this.pages = pages;
        this.current = current;
    }
}
