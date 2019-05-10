/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: NumberUtils
 * Author:   lufeiwang
 * Date:     2018/3/31 19:13
 *
 * @since 1.0.0
 */
package com.sn.gz.core.utils;

import java.math.BigDecimal;

/**
 * @author lufeiwang
 * Date 2018/3/31
 * @since 1.0.0
 */
public class NumberUtils {

    private NumberUtils() {
    }

    public static boolean isNullOrZero(Long number) {
        return null == number || number == 0;
    }

    public static boolean isNullOrZero(Integer number) {
        return null == number || number == 0;
    }

    public static boolean isNullOrZero(Double number) {
        return null == number || number == 0;
    }

    public static boolean isNullOrZero(Float number) {
        return null == number || number == 0;
    }

    public static boolean isNullOrZero(BigDecimal number) {
        return null == number || number.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * BigDecimal 相等比较.
     *
     * @param compare  the compare BigDecimal
     * @param compared the compared BigDecimal
     * @return the boolean
     */
    public static boolean eq(BigDecimal compare, BigDecimal compared) {
        return ((compare == null && compared == null)
                || ((compare != null) && (compared != null) && compare.compareTo(compared) == 0));
    }

    /**
     * BigDecimal 正负数转换
     *
     * @param source 原数
     * @return java.math.BigDecimal 装换后
     * @author Enma.ai
     * 2018/7/25
     */
    public static BigDecimal unAbs(BigDecimal source) {
        if (null == source) {
            return null;
        }
        return BigDecimal.ZERO.subtract(source);
    }

    public final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'u', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'};

    /**
     * 将十进制的数字转换为指定进制的字符串。
     *
     * @param i      十进制的数字。
     * @param system 指定的进制，常见的2/8/16。
     * @return 转换后的字符串。
     * @author xiaoxueliang
     * 2018/8/20
     */
    public static String numericToCodeString(Long i, int system) {
        Long num;
        if (i < 0) {
            num = ((long) 2 * 0x7fffffff) + i + 2;
        } else {
            num = i;
        }
        char[] buf = new char[60];
        int charPos = 60;
        while ((num / system) > 0) {
            buf[--charPos] = digits[(int) (num % system)];
            num /= system;
        }
        buf[--charPos] = digits[(int) (num % system)];
        return new String(buf, charPos, (60 - charPos));
    }

    /**
     * 将其它进制的数字（字符串形式）转换为十进制的数字。
     *
     * @param s      其它进制的数字（字符串形式）
     * @param system 指定的进制，常见的2/8/16。
     * @return 转换后的数字。
     * @author xiaoxueliang
     * 2018/8/20
     */
    public static Long codeStringToNumeric(String s, int system) {
        char[] buf = new char[s.length()];
        s.getChars(0, s.length(), buf, 0);
        long num = 0;
        for (int i = 0; i < buf.length; i++) {
            for (int j = 0; j < digits.length; j++) {
                if (digits[j] == buf[i]) {
                    num += j * Math.pow(system, buf.length - i - 1);
                    break;
                }
            }
        }
        return num;
    }
}
