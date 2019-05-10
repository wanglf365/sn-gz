/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: ApplicationContextHolder
 * Author:   xiaoxueliang
 * 2018/10/18
 */
package com.sn.gz.websupport.web.help;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文工具
 * @author xiaoxueliang
 * 2018/10/18
 */
public class ApplicationContextHolder {

    private static final String FAST_JSON_BIG_DECIMAL_SCALE = "fast_json_big_decimal_scale";

    private static final ThreadLocal<Map<String, Object>> contextThreadLocal = new ThreadLocal<>();

    /**
     * 设置fastJson转jsonString时BigDecimal小数精度
     * @param scale 小数位数
     * @author xiaoxueliang
     * 2018/10/18
     */
    public static void setBigDecimalScale(Integer scale) {
        Map<String, Object> stringObjectMap = contextThreadLocal.get();
        if (stringObjectMap == null) {
            stringObjectMap = new HashMap<>(20);
            contextThreadLocal.set(stringObjectMap);
        }
        stringObjectMap.put(FAST_JSON_BIG_DECIMAL_SCALE, scale);
    }

    /**
     * 获取fastJson转jsonString时BigDecimal小数精度
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/10/18
     */
    public static Integer getBigDecimalScale() {
        Map<String, Object> stringObjectMap = contextThreadLocal.get();
        if (stringObjectMap == null) {
            return null;
        }
        return (Integer) stringObjectMap.get(FAST_JSON_BIG_DECIMAL_SCALE);
    }
}
