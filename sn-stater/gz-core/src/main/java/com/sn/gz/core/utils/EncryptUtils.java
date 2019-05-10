/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: EncryptUtils
 * Author:   lufeiwang
 * Date:   2019/1/11
 */
package com.sn.gz.core.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加解密工具类
 *
 * @author lufeiwang
 * 2019/1/11
 */
public class EncryptUtils {

    /**
     * 加密
     *
     * @param plainContent 需要加密的内容
     * @param password     加密串
     * @return byte[]
     * @author lufeiwang
     * 2019/1/11
     */
    public static String encrypt(String plainContent, String password) {
        try {
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = plainContent.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节转十六进制
     *
     * @param array 字节数组
     * @return 十六进制
     */
    private static String parseByte2HexStr(byte[] array) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            String hex = Integer.toHexString(array[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuffer.append(hex.toUpperCase());
        }
        return stringBuffer.toString();
    }

    /**
     * 十六进制转字节数组
     *
     * @param hexStr 十六进制
     * @return 字节数组
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 解密
     *
     * @param cipherText 需要解密的密文
     * @param password   迷药串
     * @return byte[]
     * @author lufeiwang
     * 2019/1/11
     */
    public static String decrypt(String cipherText, String password) {
        try {
            byte[] content;
            content = parseHexStr2Byte(cipherText);
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(content);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密钥
     *
     * @param strKey 密串
     * @return 密钥
     */
    public static SecretKey getKey(String strKey) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            keyGenerator.init(128, secureRandom);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("初始化密钥出现异常");
        }
    }

    public static void main(String []arg)
    {
        System.out.println(encrypt("123456","SN190418"));
    }
}
