/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Filter
 * Author:   xiaoxueliang
 * Date:     2018/7/7 10:08
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.query;

import java.io.Serializable;
import java.util.List;

/**
 * 过滤封装类
 * @author xiaoxueliang
 * 2018/7/7 17:37
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Filter implements Serializable {

    /**
     * 字段名
     */
    private String property;
    /**
     * 过滤类型
     */
    private FilterType filterType;
    /**
     * like模式，左、右、两边、自定义
     */
    private SqlLike sqlLike;
    /**
     * 最大条数
     */
    private Integer count;

    /**
     * 过滤取值
     */
    private Object value;

    /**
     * 过滤取值2
     */
    private Object value2;

    private Filter(Integer count){
        this.count = count;
        this.filterType = FilterType.count;
    }

    private Filter(String property, FilterType filterType, Object value) {
        this.property = property;
        this.value = value;
        this.filterType = filterType;
    }

    private Filter(String property, FilterType filterType, Object value, SqlLike sqlLike) {
        this.property = property;
        this.value = value;
        this.filterType = filterType;
        this.sqlLike = sqlLike;
    }

    private Filter(String property, FilterType filterType, Object value, Object value2) {
        this.property = property;
        this.value = value;
        this.value2 = value2;
        this.filterType = filterType;
    }


    /**
     * 或
     * @return Filter
     */
    public static Filter or() {
        return new Filter(null, FilterType.or, null);
    }

    /**
     * like查询模式
     * @author xiaoxueliang
     * 2018/7/7 18:12
     * @since 1.0.0
     */
    public enum SqlLike {
        /**
         * LEFT，"左边%"
         */
        LEFT,
        /**
         * RIGHT，"右边%"
         */
        RIGHT,
        /**
         * CUSTOM，自定义
         */
        CUSTOM,
        /**
         * DEFAULT，%两边%
         */
        DEFAULT
    }

    /**
     * 获取过滤组装类
     * @author 肖学良<br>
     * Date: 2015年12月24日
     */
    public static FilterBuilder build() {
        return new FilterBuilder();
    }

    /**
     * 等于
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter eq(String property, Object value) {
        return new Filter(property, FilterType.eq, value);
    }

    /**
     * 不等于
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter ne(String property, Object value) {
        return new Filter(property, FilterType.ne, value);
    }

    /**
     * 大于
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter gt(String property, Object value) {
        return new Filter(property, FilterType.gt, value);
    }

    /**
     * 小于
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter lt(String property, Object value) {
        return new Filter(property, FilterType.lt, value);
    }

    /**
     * 大于或等于
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter ge(String property, Object value) {
        return new Filter(property, FilterType.ge, value);
    }

    /**
     * 小于或等于
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter le(String property, Object value) {
        return new Filter(property, FilterType.le, value);
    }

    /**
     * 模糊查询
     * @param property 属性名称
     * @param value    属性值
     * @param sqlLike  like模式
     * @return Filter
     */
    public static Filter like(String property, Object value, SqlLike sqlLike) {
        return new Filter(property, FilterType.like, value, sqlLike);
    }

    /**
     * in
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter in(String property, List<?> value) {
        return new Filter(property, FilterType.in, value);
    }

    /**
     * notIn
     * @param property 属性名称
     * @param value    属性值
     * @return Filter
     */
    public static Filter notIn(String property, List<?> value) {
        return new Filter(property, FilterType.notIn, value);
    }

    /**
     * 为null
     * @param property 属性名称
     * @return Filter
     */
    public static Filter isNull(String property) {
        return new Filter(property, FilterType.isNull, null);
    }

    /**
     * 不为null
     * @param property 属性名称
     * @return Filter
     */
    public static Filter isNotNull(String property) {
        return new Filter(property, FilterType.isNotNull, null);
    }

    /**
     * and换行
     * @return Filter
     */
    public static Filter andNew() {
        return new Filter(null, FilterType.andNew, null);
    }

    /**
     * 或换行
     * @return Filter
     */
    public static Filter orNew() {
        return new Filter(null, FilterType.orNew, null);
    }

    /**
     * 过滤类型枚举
     *
     * @author xiaoxueliang
     * 2018/7/7 17:39
     * @since 1.0.0
     */
    public enum FilterType {
        /**
         * 等于
         */
        eq,
        /**
         * 不等于
         */
        ne,
        /**
         * 大于
         */
        gt,
        /**
         * 小于
         */
        lt,
        /**
         * 大于或等于
         */
        ge,
        /**
         * 小于或等于
         */
        le,
        /**
         * 模糊查询
         */
        like,
        /**
         * in
         */
        in,
        /**
         * not in
         */
        notIn,
        /**
         * 为null
         */
        isNull,
        /**
         * 不为null
         */
        isNotNull,
        /**
         * and：新的条件分组
         */
        andNew,
        /**
         * or：或
         */
        or,
        /**
         * or：新的条件分组
         */
        orNew,
        /**
         * 之间
         */
        between,
        /**
         * 行级悲观锁
         */
        forUpdate,
        /**
         * 最大返回条数
         */
        count


    }

    /**
     * between
     * @param property 属性名称
     * @param value    属性值
     * @param value2   属性值2
     * @return Filter
     */
    public static Filter between(String property, Object value, Object value2) {
        return new Filter(property, FilterType.between, value, value2);
    }

    /**
     * 加悲观锁
     * @return Filter
     */
    public static Filter forUpdate() {
        return new Filter(null, FilterType.forUpdate, null);
    }

    /**
     * 限制还回条数
     * @param count 条数
     * @return Filter
     */
    public static Filter count(Integer count){
        return new Filter(count);
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }


    public FilterType getFilterType() {
        return this.filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue2() {
        return this.value2;
    }

    public SqlLike getSqlLike() {
        return this.sqlLike;
    }

    public Integer getCount() {
        return this.count;
    }
}
