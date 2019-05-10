/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: PageUtil
 * Author:   xiaoxueliang
 * 2018/7/12 19:13
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.utils;

import com.baomidou.mybatisplus.plugins.Page;
import com.sn.gz.core.dto.PageOutDTO;
import com.sn.gz.jdbc.starter.query.PageWrapper;

import java.util.List;

/**
 * 分页工具
 * @author xiaoxueliang
 * 2018/7/12 19:13
 * @since 1.0.0
 */
public class PageUtil {

    @SuppressWarnings("unused")
    public static <T> Page<T> map(PageModel pageModel, Class<T> clazz) {
        Page<T> page = new Page<>();
        page.setCurrent(pageModel.getCurrent());
        page.setSize(pageModel.getSize());
        return page;
    }

    public static <T> PageWrapper<T> map(List<T> date, Page page) {
        PageWrapper<T> localPageWrapper = new PageWrapper<>();
        localPageWrapper.setData(date);
        localPageWrapper.setCurrent(page.getCurrent());
        localPageWrapper.setSize(page.getSize());
        localPageWrapper.setTotal(page.getTotal());
        return localPageWrapper;
    }

    public static <T> PageOutDTO<T> map(List<T> data, PageWrapper<?> pageWrapper) {
        PageOutDTO<T> pageOutDTO = new PageOutDTO<>();
        pageOutDTO.setData(data);
        pageOutDTO.setCurrent(pageWrapper.getCurrent());
        pageOutDTO.setSize(pageWrapper.getSize());
        pageOutDTO.setPages(pageWrapper.getPages());
        pageOutDTO.setTotal(pageWrapper.getTotal());
        return pageOutDTO;
    }
}
