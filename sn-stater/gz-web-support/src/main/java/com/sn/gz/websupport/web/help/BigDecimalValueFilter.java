/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BigDecimalValueFilter
 * Author:   xiaoxueliang
 * 2018/10/18
 */
package com.sn.gz.websupport.web.help;

import com.alibaba.fastjson.serializer.ValueFilter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal精度处理器
 * @author xiaoxueliang
 * 2018/10/18
 */
public class BigDecimalValueFilter implements ValueFilter {


    @Override
    public Object process(Object object, String name, Object value) {
        Integer bigDecimalScale = ApplicationContextHolder.getBigDecimalScale();
        if(value != null && value instanceof BigDecimal && bigDecimalScale != null && bigDecimalScale > 0) {
            BigDecimal bigDecimalValue = (BigDecimal)value;
            return bigDecimalValue.setScale(bigDecimalScale, RoundingMode.HALF_UP).toPlainString();
        }
        return value;
    }
}
