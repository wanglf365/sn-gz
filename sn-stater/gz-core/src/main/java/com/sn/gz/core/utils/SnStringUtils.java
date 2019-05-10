/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DianjiStringUtils
 * Author:   xiaoxueliang
 * Date:     2018/4/16 14:33
 *
 * @since 1.0.0
 */
package com.sn.gz.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author xiaoxueliang
 * Date 2018/4/16
 * @since 1.0.0
 */
public class SnStringUtils {

    public static final String SEPARATOR_EN = ",";
    public static final String SEPARATOR_ZH = "，";
    /**
     * 手机正则匹配
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1\\d{10}$");
    /**
     * 临时令牌正则匹配
     */
    private static final Pattern TEMP_TOKEN_PATTERN = Pattern.compile("^[0-9a-zA-Z]{32}$");

    private SnStringUtils() {
    }

    /**
     * 是否是合法的手机格式
     *
     * @param phone 手机号字符串
     * @return boolean
     * @author xiaoxueliang
     * Date: 2018/4/16 14:42
     * @since 1.0.0
     */
    public static boolean isPhoneNumber(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }

    /**
     * 是否合法的临时令牌
     *
     * @param tempToken 临时令牌
     * @return boolean
     * @author xiaoxueliang
     * Date: 2018/4/16 14:48
     * @since 1.0.0
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isTempToken(String tempToken) {
        if (StringUtils.isBlank(tempToken)) {
            return false;
        }
        Matcher matcher = TEMP_TOKEN_PATTERN.matcher(tempToken);
        return matcher.matches();
    }

    /**
     * 切割字符串
     *
     * @param stringArray 字符串数组
     * @param separator   分割符
     * @param removeExist 是否去掉存在的
     * @return java.util.List<java.lang.String>
     * @author xiaoxueliang
     * 2018/5/4 16:30
     * @since 1.0.0
     */
    public static Collection<String> split(String[] stringArray, String separator, boolean removeExist) {
        List<String> stringList = new ArrayList<>();
        for (String string : stringArray) {
            if (StringUtils.isNotBlank(string)) {
                String[] newStringArray = string.split(separator);
                for (String newString : newStringArray) {
                    if (StringUtils.isNotBlank(newString)) {
                        stringList.add(newString);
                    }
                }
            }
        }
        if (removeExist) {
            return new HashSet<>(stringList);
        }
        return stringList;
    }

    /**
     * 分割字符串并转换为List
     *
     * @param source    待分割字符串
     * @param separator 分割字符串
     * @return java.util.List&lt;java.lang.String&gt;
     * @author xiaoxueliang
     * 2018/7/15 10:48
     * @since 1.0.0
     */
    public static List<String> splitAnd2List(String source, String separator) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(source)) {
            return list;
        }
        if (StringUtils.isBlank(separator)) {
            list.add(source);
            return list;
        }
        String[] stringArray = source.split(separator);
        list.addAll(Arrays.asList(stringArray));
        return list;
    }

    /**
     * long类型集合转换成String集合
     *
     * @param longList long类型集合
     * @return java.util.List String类型集合
     * @author Enma.ai
     * 2018/8/28
     */
    public static List<String> listLong2String(List<Long> longList) {
        List<String> stringList = new ArrayList<>();
        if (ListUtils.isNotNull(longList)) {
            for (Long aLong : longList) {
                stringList.add(aLong.toString());
            }
        }
        return stringList;
    }

    /**
     * string类型集合转成long集合
     *
     * @param stringList string类型集合
     * @return java.util.List long类型集合
     * @author Enma.ai
     * 2018/8/28
     */
    public static List<Long> listString2Long(List<String> stringList) {
        List<Long> longList = new ArrayList<>();
        if (ListUtils.isNotNull(stringList)) {
            for (String str : stringList) {
                longList.add(Long.valueOf(str));
            }
        }
        return longList;
    }
}
