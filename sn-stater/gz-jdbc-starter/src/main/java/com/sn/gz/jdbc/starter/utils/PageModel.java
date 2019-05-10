/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: PageInDTO
 * Author:   xiaoxueliang
 * Date:     2018/4/13 10:49
 *
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.utils;

import lombok.Data;

/**
 * 分页Model
 * @author xiaoxueliang
 * 2018/4/13
 * @since 1.0.0
 */
@Data
public class PageModel<T> {

    /**
     * 第几页
     */
    private int current = 1;
    /**
     * 每页条数
     */
    private int size = 10;

}
