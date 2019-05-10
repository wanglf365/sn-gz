/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: OrderBy
 * Author:   xiaoxueliang
 * Date:     2018/7/7 10:08
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.query;

import java.io.Serializable;

/**
 * 排序封装类
 * @author xiaoxueliang
 * 2018/7/7 19:42
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class OrderBy implements Serializable {

    /**
     * 排序字段
     */
    private String property;
    /**
     * 排序方式
     */
    private OrderType orderType;


    private OrderBy(String property, OrderType orderType) {
        this.property = property;
        this.orderType = orderType;
    }

    /**
     * 排序方式枚举
     */
    public enum OrderType {
        ASC, DESC
    }

    /**
     * 获取排序组装类
     * @return com.dianji.pangu.jdbc.query.SortBuilder
     * @author xiaoxueliang
     * 2018/7/7 19:44
     * @since 1.0.0
     */
    public static OrderByBuilder build() {
        return new OrderByBuilder();
    }

    /**
     * 获取升序排序
     * @param property 排序属性
     * @return com.dianji.pangu.jdbc.query.Sort
     * @author xiaoxueliang
     * 2018/7/7 19:45
     * @since 1.0.0
     */
    public static OrderBy asc(String property) {
        return new OrderBy(property, OrderType.ASC);
    }

    /**
     * 获取降序排序
     * @param property 排序属性
     * @return com.dianji.pangu.jdbc.query.Sort
     * @author xiaoxueliang
     * 2018/7/7 19:45
     * @since 1.0.0
     */
    public static OrderBy desc(String property) {
        return new OrderBy(property, OrderType.DESC);
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
