/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: EntityWrapper
 * Author:   hanson
 * Date:   2018/6/26
 */
package com.sn.gz.jdbc.starter.query;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对mybatis-plus的包装,支持类字段调用，负责将所有的类字段调用替换成列名调用
 *
 * @author hanson
 * 2018/6/26
 */
@SuppressWarnings("unused")
public class EntityClassWrapper<T> extends com.baomidou.mybatisplus.mapper.EntityWrapper<T>{
    private static volatile ConcurrentHashMap<String,HashMap<String,String>> dbFieldMaps = new ConcurrentHashMap<>(64);
    /**
     * 当前的类
     */
    private Class<T> clazz;

    public EntityClassWrapper(Class<T> cls){
        super();
        this.clazz = cls;
    }

    /**
     * 读取指定类的字段映射关系map
     * @param clazz 类
     * @return HashMap<String,String> 类的字段映射关系map
     */
    private static HashMap<String,String> getDbFieldMap(Class clazz){
        return dbFieldMaps.get(clazz.getName());
    }

    /**
     * 设置类的字段映射map到dbFieldMaps
     * @param clazz 类
     * @param dbFieldMap 类的字段映射map
     */
    private static synchronized void setDbFieldMap(Class clazz,HashMap<String,String>dbFieldMap) {
        if (getDbFieldMap(clazz) == null) {
            dbFieldMaps.put(clazz.getName(), dbFieldMap);
        }
    }

    /**
     * 使用正则表达式来判断字符串中是否包含字母
     * @param str 待检验的字符串
     * @return 返回是否包含
     * true: 包含字母 ;false 不包含字母
     */
    private boolean judgeContainsStr(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(str);
        return m.matches();
    }

    /**
     * 对sql语句中的字段替换成数据库的列名，都是以=形式
     * @param sqlField field的sql语句
     * @return String 转换完的字符串
     */
    protected String getSqlColumns(String sqlField){
        if (sqlField != null) {
            if (!judgeContainsStr(sqlField)) {
                return sqlField;
            }
        }
        StringBuilder result = new StringBuilder();
        String[] subs = sqlField.split("=");

        for(int i = 0;i < subs.length-1 ;i++){
            result.append(replaceFieldName(subs[i])).append("=");
        }
        // 增加最后一段，最后一段不可能有字段名
        result.append(subs[subs.length-1]);
        return result.toString();
    }

    /**
     * 字段字符串替换成数据库的列名，都是以field,field形式拼接，如GroupBy,OrderBy中使用
     * @param fields 字段','拼接的字符串
     * @return String 转换完的字符串
     */
    protected String getColumns(String fields ){
        StringBuilder result = new StringBuilder();
        String[] subs = fields.split(",");

        for(int i = 0;i < subs.length-1 ;i++){
            result.append(replaceFieldName(subs[i])).append(",");
        }
        // 增加最后一段，没有分隔符
        result.append(replaceFieldName(subs[subs.length-1]));
        return result.toString();
    }

    /**
     * 判断字符是否是字母或数字
     * @param ch 字符
     * @return boolean判断结果
     */
    private static boolean isLetterAndNumber(char ch){
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9');
    }
    /**
     * 从输入的字符串找到字段名替换成列名
     * @param str 字符串
     * @return 替换完的串
     */
    private String replaceFieldName(String str){
        // 从后面找到该单词，并替换成db字段
        StringBuilder result = new StringBuilder();
        boolean hasFindEnd = false;
        int end = str.length();
        int start = 0;

        // 从后开始找字段在的最后的位置和开始位置
        for (int pos = str.length()-1;pos >= 0; pos--){
            char ch = str.charAt(pos);
            if(!hasFindEnd) {
                // 先找字段的最后位置
                if (isLetterAndNumber(ch)) {
                    end = pos + 1;
                    hasFindEnd = true;
                }
            }else {
                if (!isLetterAndNumber(ch)) {
                    start = pos + 1;
                    break;
                }
            }
        }
        result.append(str.substring(0,start)).append(getDbColumnName(str.substring(start,end)));
        // 没结束
        if (end < str.length()) {
            result.append(str.substring(end,str.length()));
        }
        return result.toString();
    }
    /**
     * 将字段参数map转换成列名参数map
     * @param fieldParams 字段参数map
     * @return 列名参数map
     */
    private Map<String, Object> getColumnParams(Map<String, Object> fieldParams){
        Map<String, Object> colParams = new HashMap<>(24);

        for (Map.Entry<String, Object> entry : fieldParams.entrySet()) {
            colParams.put(getDbColumnName(entry.getKey()),entry.getValue());
        }
        return colParams;
    }

    @Override
    public Wrapper<T> where(boolean condition, String sqlWhere, Object... params) {
        return super.where(condition, getSqlColumns(sqlWhere),params);
    }

    @Override
    public Wrapper<T> eq(boolean condition, String column, Object params) {
        return super.eq(condition, getDbColumnName(column),params);
    }

    @Override
    public Wrapper<T> ne(boolean condition, String column, Object params) {
        return super.ne(condition, getDbColumnName(column),params);
    }
    @Override
    public Wrapper<T> allEq(boolean condition, Map<String, Object> params) {
        return super.allEq(condition,getColumnParams(params));
    }
    /**
     * <p>
     * 等同于SQL的"field>value"表达式
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName 实体类字段名
     * @param value 值
     * @return this
     */
    @Override
    public Wrapper<T> gt(boolean condition, String fieldName, Object value) {
        return super.gt(condition, getDbColumnName(fieldName),value);
    }

    /**
     * <p>
     * 等同于SQL的"field>=value"表达式
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName 实体类字段名
     * @param value 值
     * @return this
     */
    @Override
    public Wrapper<T> ge(boolean condition, String fieldName, Object value) {
        return super.ge(condition, getDbColumnName(fieldName),value);
    }

    /**
     * <p>
     * 等同于SQL的"field<value"表达式
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName 实体类字段名
     * @param value 值
     * @return this
     */
    @Override
    public Wrapper<T> lt(boolean condition, String fieldName, Object value) {
        return super.lt(condition, getDbColumnName(fieldName),value);
    }

    /**
     * <p>
     * 等同于SQL的"field<=value"表达式
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName 实体类字段名
     * @param value 值
     * @return this
     */
    @Override
    public Wrapper<T> le(boolean condition, String fieldName, Object value) {
        return super.le(condition, getDbColumnName(fieldName),value);
    }

    @Override
    public Wrapper<T> and(boolean condition, String sqlAnd, Object... params) {
        return super.and(condition, getSqlColumns(sqlAnd),params);
    }

    @Override
    public Wrapper<T> andNew(boolean condition, String sqlAnd, Object... params) {
        return super.andNew(condition, getSqlColumns(sqlAnd),params);
    }

    @Override
    public Wrapper<T> or(boolean condition, String sqlOr, Object... params) {
        return super.or(condition, getSqlColumns(sqlOr),params);
    }

    @Override
    public Wrapper<T> orNew(boolean condition, String sqlOr, Object... params) {
        return super.orNew(condition, getSqlColumns(sqlOr),params);
    }
    /**
     * <p>
     * SQL中groupBy关键字跟的条件语句
     * </p>
     * <p>
     * eg: ew.where("name='zhangsan'").groupBy("id,name")
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldNames   SQL 中的 Group by 语句，无需输入 Group By 关键字
     * @return this
     */
    @Override
    public Wrapper<T> groupBy(boolean condition, String fieldNames) {
        return super.groupBy(condition, getColumns(fieldNames));
    }

    @Override
    public Wrapper<T> having(boolean condition, String sqlHaving, Object... params) {
        return super.having(condition, getSqlColumns(sqlHaving),params);
    }
    /**
     * <p>
     * SQL中orderby关键字跟的条件语句
     * </p>
     * <p>
     * eg: ew.groupBy("id,name").having("id={0}",22).and("password is not null"
     * ).orderBy("id,name")
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldNames   SQL 中的 order by 语句，无需输入 Order By 关键字
     * @return this
     */
    @Override
    public Wrapper<T> orderBy(boolean condition, String fieldNames) {
        return super.orderBy(condition, getColumns(fieldNames));
    }

    /**
     * <p>
     * SQL中orderby关键字跟的条件语句，可根据变更动态排序
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldNames   SQL 中的 order by 语句，无需输入 Order By 关键字
     * @param isAsc     是否为升序
     * @return this
     */
    @Override
    public Wrapper<T> orderBy(boolean condition, String fieldNames, boolean isAsc) {
        return super.orderBy(condition, getColumns(fieldNames),isAsc);
    }
    /**
     * <p>
     * LIKE条件语句，value中无需前后%
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值
     * @return this
     */
    @Override
    public Wrapper<T> like(boolean condition, String fieldName, String value) {
        return super.like(condition, getDbColumnName(fieldName),value);
    }
    /**
     * <p>
     * NOT LIKE条件语句，value中无需前后%
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值
     * @return this
     */
    @Override
    public Wrapper<T> notLike(boolean condition, String fieldName, String value) {
        return super.notLike(condition, getDbColumnName(fieldName),value);
    }
    /**
     * <p>
     * LIKE条件语句，value中无需前后%
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值
     * @param type SqlLike
     * @return this
     */
    @Override
    public Wrapper<T> like(boolean condition, String fieldName, String value, SqlLike type) {
        return super.like(condition, getDbColumnName(fieldName),value,type);
    }
    /**
     * <p>
     * NOT LIKE条件语句，value中无需前后%
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值
     * @param type SqlLike
     * @return this
     */
    @Override
    public Wrapper<T> notLike(boolean condition, String fieldName, String value, SqlLike type) {
        return super.notLike(condition, getDbColumnName(fieldName),value,type);
    }

    /**
     * <p>
     * is not null 条件
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldNames   字段名称。多个字段以逗号分隔。
     * @return this
     */
    @Override
    public Wrapper<T> isNotNull(boolean condition, String fieldNames) {
        return super.isNotNull(condition, getColumns(fieldNames));
    }
    /**
     * <p>
     * is  null 条件
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldNames   字段名称。多个字段以逗号分隔。
     * @return this
     */
    @Override
    public Wrapper<T> isNull(boolean condition, String fieldNames) {
        return super.isNull(condition, getColumns(fieldNames));
    }
    /**
     * <p>
     * IN 条件语句，目前适配mysql及oracle
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     逗号拼接的字符串
     * @return this
     */
    @Override
    public Wrapper<T> in(boolean condition, String fieldName, String value) {
        return super.in(condition, getDbColumnName(fieldName),value);
    }
    /**
     * <p>
     * IN 条件语句，目前适配mysql及oracle
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值 List集合
     * @return this
     */
    @Override
    public Wrapper<T> in(boolean condition, String fieldName, Collection<?> value) {
        return super.in(condition, getDbColumnName(fieldName),value);
    }

    /**
     * <p>
     * IN 条件语句，目前适配mysql及oracle
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值 object数组
     * @return this
     */
    @Override
    public Wrapper<T> in(boolean condition, String fieldName, Object[] value) {
        return super.in(condition, getDbColumnName(fieldName),value);
    }
    /**
     * <p>
     * NOT IN条件语句
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     逗号拼接的字符串
     * @return this
     */
    @Override
    public Wrapper<T> notIn(boolean condition, String fieldName, String value) {
        return super.notIn(condition, getDbColumnName(fieldName),value);
    }
    /**
     * <p>
     * NOT IN 条件语句，目前适配mysql及oracle
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值 List集合
     * @return this
     */
    @Override
    public Wrapper<T> notIn(boolean condition, String fieldName, Collection<?> value) {
        return super.notIn(condition, getDbColumnName(fieldName),value);
    }

    /**
     * <p>
     * NOT IN 条件语句，目前适配mysql及oracle
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param value     匹配值 object数组
     * @return this
     */
    @Override
    public Wrapper<T> notIn(boolean condition, String fieldName, Object... value) {
        return super.notIn(condition, getDbColumnName(fieldName),value);
    }

    /**
     * <p>
     * betwwee 条件语句
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param val1 取值范围1
     * @param val2 取值范围2
     * @return this
     */
    @Override
    public Wrapper<T> between(boolean condition, String fieldName, Object val1, Object val2) {
        return super.between(condition, getDbColumnName(fieldName), val1, val2);
    }

    /**
     * <p>
     * NOT betwwee 条件语句
     * </p>
     *
     * @param condition 拼接的前置条件
     * @param fieldName    字段名称
     * @param val1 取值范围1
     * @param val2 取值范围2
     * @return this
     */
    @Override
    public Wrapper<T> notBetween(boolean condition, String fieldName, Object val1, Object val2) {
        return super.notBetween(condition, getDbColumnName(fieldName), val1, val2);
    }
    /**
     * 根据实体类字段名，获取对应的数据库表字段名，第一次建立类对应Map
     *
     * @param fieldName 实体类字段名
     * @return the string
     */
    private String getDbColumnName(String fieldName) {
        HashMap<String, String> dbFieldMap = getDbFieldMap(clazz);

        // 当前类没有则新建对应的map,ConcurrentHashMap value不允许是null
        if (dbFieldMap == null) {
            // 容量按字段数16/0.75 + 1 估算
            dbFieldMap = new HashMap<>(24);

            Field[] fields = getClassAllDeclaredFields();
            // 过滤带数据库字段映射关系的字段
            for (Field field : fields) {
                TableField fieldAnnotation = field.getAnnotation(TableField.class);
                if (fieldAnnotation != null && !fieldAnnotation.value().isEmpty()) {
                    dbFieldMap.put(field.getName(), fieldAnnotation.value());
                }
            }
            setDbFieldMap(clazz,dbFieldMap);
        }
        return dbFieldMap.get(fieldName);
    }

    /**
     * 获取当前对象类所有包括（父类，祖先，到object类为止）所有申明的字段（public，protected，private）
     *
     * @return java.lang.reflect.Field[]
     */
    private Field[] getClassAllDeclaredFields() {
        List<Field> allFieldList = new ArrayList<>();

        for (Class<?> curClass = clazz; curClass != Object.class; curClass = curClass.getSuperclass()) {
            Field[] fields = curClass.getDeclaredFields();
            allFieldList.addAll(Arrays.asList(fields));
        }
        return allFieldList.toArray(new Field[allFieldList.size()]);
    }

}
