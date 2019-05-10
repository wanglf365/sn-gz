/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Page
 * Author:   xiaoxueliang
 * Date:     2018/7/7 10:08
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.query;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页封装类
 * @author xiaoxueliang
 * 2018/7/7 11:53
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class PageWrapper<T> implements Serializable {

    /**
     * 每页最大条数
     */
    private static final int MAX_PAGE_SIZE = 200;
    /**
     * 当前第几页
     */
    private int current = 1;
    /**
     * 每页显示条数，默认 10
     */
    private int size = 10;
    /**
     * 总条数
     */
    private int total = 0;
    /**
     * 查询排序集
     */
    private List<OrderBy> orderByList = new ArrayList<>();
    /**
     * 查询过滤集
     */
    private List<Filter> filters = new ArrayList<>();
    /**
     * 数据
     */
    private List<T> data;

    /**
     * 不分页查询时，默认注入最大页数和第一页
     *
     * @author Enma.ai
     * 2018/8/22
     */
    public void notPaged() {
        this.current = 1;
        this.size = MAX_PAGE_SIZE;
    }

    /**
     * 添加过滤条件
     * @param filter 过滤条件
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @author xiaoxueliang
     * 2018/7/7 12:06
     * @since 1.0.0
     */
    public PageWrapper add(Filter filter) {
        filters.add(filter);
        return this;
    }

    /**
     * 添加排序
     * @author 肖学良<br>
     * Date: 2015年12月24日
     */
    public PageWrapper add(OrderBy orderBy) {
        orderByList.add(orderBy);
        return this;
    }

    /**
     * 清除：所有过滤条件
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @author xiaoxueliang
     * 2018/7/7 12:07
     * @since 1.0.0
     */
    public PageWrapper clearFilters() {
        filters.clear();
        return this;
    }

    /**
     * 清除：所有排序
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @author xiaoxueliang
     * 2018/7/7 12:08
     * @since 1.0.0
     */
    public PageWrapper clearSorts() {
        orderByList.clear();
        return this;
    }

    /**
     * 清除：过滤集，排序集
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @author xiaoxueliang
     * 2018/7/7 12:09
     * @since 1.0.0
     */
    public PageWrapper clearAll() {
        orderByList.clear();
        filters.clear();
        return this;
    }

    /**
     * 获取总记录数
     * @return int
     * @author xiaoxueliang
     * 2018/7/7 12:15
     * @since 1.0.0
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     * @param total 设置总记录数
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @author xiaoxueliang
     * 2018/7/7 12:16
     * @since 1.0.0
     */
    public PageWrapper setTotal(int total) {
        this.total = total;
        return this;
    }

    /**
     * 获取当前页
     * @return int
     * @author xiaoxueliang
     * 2018/7/7 12:17
     * @since 1.0.0
     */
    public int getCurrent() {
        return this.current;
    }

    /**
     * 设置当前页
     * @param current 当前页
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @author xiaoxueliang
     * 2018/7/7 12:18
     * @since 1.0.0
     */
    public PageWrapper setCurrent(int current) {
        this.current = current;
        return this;
    }

    /**
     * 获取每页最大记录数
     * @return int
     * @author xiaoxueliang
     * 2018/7/7 12:18
     * @since 1.0.0
     */
    public int getSize() {
        return this.size;
    }

    /**
     * 设置每页最大记录数
     * @author 肖学良<br>
     * Date: 2015年12月24日
     */
    public PageWrapper setSize(int size) {
        if (size < 1) {
            this.size = 1;
        } else if (size > MAX_PAGE_SIZE) {
            this.size = MAX_PAGE_SIZE;
        } else {
            this.size = size;
        }
        return this;
    }

    /**
     * 获取总页数
     * @return int
     * @author xiaoxueliang
     * 2018/7/7 12:20
     * @since 1.0.0
     */
    public int getPages() {
        int pages = (total / size);
        if (total % size > 0) {
            pages += 1;
        }
        return pages;
    }

    /**
     * 获取排序集
     * @return java.util.List&lt;com.dianji.pangu.jdbc.query.Sort&gt;
     * @since 1.0.0
     * @author xiaoxueliang
     * 2018/7/7 12:23
     */
    public List<OrderBy> getOrderByList() {
        return orderByList;
    }

    /**
     * 获取过滤集
     * @return java.util.List&lt;com.dianji.pangu.jdbc.query.Filter&gt;
     * @since 1.0.0
     * @author xiaoxueliang
     * 2018/7/7 12:24
     */
    public List<Filter> getFilters() {
        return filters;
    }

    /**
     * 获取查询结果集
     * @return java.util.List
     * @since 1.0.0
     * @author xiaoxueliang
     * 2018/7/7 12:24
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置查询结果集
     * @param data 数据
     * @return com.dianji.pangu.jdbc.query.PageWrapper
     * @since 1.0.0
     * @author xiaoxueliang
     * 2018/7/7 12:25
     */
    public PageWrapper<T> setData(List<T> data) {
        this.data = data;
        return this;
    }
}
