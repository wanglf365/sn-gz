/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Md5Utils
 * Author:   luffy
 * Date:   2018/6/1 15:59
 * Since: 1.0.0
 */
package com.sn.gz.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加解密工具类
 *
 * @author luffy
 * @since 1.0.0
 * 2018/6/1
 */
public class Md5Utils {

    private static final String PASSWORD_PREFIX = "SN";

    /**
     * 传入密码明文，通过md5加密后返回密文
     *
     * @param password 密码明文
     * @return java.lang.String
     * @author luffy
     * 2018/6/1 16:01
     * @since 1.0.0
     */
    public static String generatePasswordMD5(String password) {
        if (StringUtils.isEmpty(password)) {
            return "";
        }
        String source = password + PASSWORD_PREFIX;
        MessageDigest messageDigest;
        try {
            messageDigest = getMessageDigest(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();

        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & b));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & b));
            }
        }
        return md5StrBuff.toString();
    }

    /**
     * 获取摘要
     *
     * @param str 原字符串
     * @return MessageDigest
     * @throws Exception Exception
     */
    private static MessageDigest getMessageDigest(String str) throws Exception {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return digest;
    }

    /**
     * 生成md5串
     *
     * @param str 原字符串
     * @return 密码
     */
    public static String generateMD5(String str) {
        MessageDigest digest = null;
        try {
            digest = getMessageDigest(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = digest.digest();
        StringBuilder md5StrBuff = new StringBuilder();

        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & b));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & b));
            }
        }
        return md5StrBuff.toString();
    }

    /**
     * 传进明文和密文
     *
     * @param source 入参明文
     * @param target 入参密文
     * @return boolean
     * @author luffy
     * 2018/6/1 16:24
     * @since 1.0.0
     */
    public static boolean verify(String source, String target) {
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(target)) {
            return false;
        }
        String sourceMd5 = generatePasswordMD5(source);
        return (sourceMd5 != null) && sourceMd5.equals(target);
    }

    public static void main(String []arg)
    {
        String str ="123456";
        System.out.println(generatePasswordMD5(str));
    }
}
