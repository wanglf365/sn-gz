/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: CustomizeMetaObjectHandler
 * Author:   xiaoxueliang
 * Date:     2018/3/27 12:59
 *
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.sn.gz.core.utils.IdGenerator;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 字段自动填充拦截器
 *  自动填充id, createDatetime, modifiedDatetime
 * @author xiaoxueliang
 * Date 2018/3/27
 * @since 1.0.0
 */
public class CustomizeMetaObjectHandler extends MetaObjectHandler {

    /**
     * insert回调
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        Object id = getFieldValByName("id", metaObject);
        Object createDatetime = getFieldValByName("createDatetime", metaObject);
        Object modifiedDatetime = getFieldValByName("modifiedDatetime", metaObject);
        if(id == null){
            setFieldValByName("id", IdGenerator.getInstance().nextId(), metaObject);
        }
        if (createDatetime == null) {
            setFieldValByName("createDatetime", new Date(), metaObject);
        }
        if(modifiedDatetime == null){
            setFieldValByName("modifiedDatetime", new Date(), metaObject);
        }
    }

    /**
     * update回调
     * @param metaObject meta对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //更新填充
        setFieldValByName("modifiedDatetime", new Date(), metaObject);
    }
}
