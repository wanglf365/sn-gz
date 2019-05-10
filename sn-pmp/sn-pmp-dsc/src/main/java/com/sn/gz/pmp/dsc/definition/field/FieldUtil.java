/*
 * Copyright (C), 2018-2019, ShinianData
 * FileName: FieldUtil
 * Author:   hanson
 * Date:   2019/4/24
 */
package com.sn.gz.pmp.dsc.definition.field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sn.gz.pmp.dsc.definition.field.bo.element.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义字段转换工具类
 *
 * @author hanson
 * 2019/4/24
 */
public class FieldUtil {
    /**
     * 从字段列表的json串中获取准确字段列表
     * @param jsonStr json串
     * @return List<FieldBase>
     */
    static public List<FieldBase> getFieldList(String jsonStr){
        List<FieldBase> list = new ArrayList<>();
        try {
            List<JSONObject> list2= JSON.parseArray(jsonStr,JSONObject.class);
            for(JSONObject obj:list2){
                FieldBase field = makeFieldByType(obj.getString(FieldBase.DATA_TYPE));
                field = obj.toJavaObject(field.getClass());
                list.add(field);
            }
        }catch (Exception e){
            return list;
        }
        return list;
    }

    /**
     * 根据字段类型创建字段类
     * @param type 字段类型
     * @return FieldBase
     */
    @SuppressWarnings("WeakerAccess")
    static public FieldBase makeFieldByType(String type) {
        FieldType fieldType = FieldType.valueOf(type);
        return makeFieldByType(fieldType);
    }

    /**
     * 根据字段枚举类型创建字段类
     * @param fieldType 字段类型
     * @return FieldBase
     */
    @SuppressWarnings("WeakerAccess")
    static public FieldBase makeFieldByType(FieldType fieldType){
        FieldBase field = null;

        switch (fieldType){
            case Boolean:
                field = new BooleanField();
                break;
            case URL:
                field = new URLField();
                break;
            case Date:
                field = new DateField();
                break;
            case File:
                field = new FileAttachmentField();
                break;
            case Long:
                field = new LongField();
                break;
            case Text:
                field = new TextField();
                break;
            case Time:
                field = new TimeField();
                break;
            case Email:
                field = new EmailField();
                break;
            case Image:
                field = new ImageField();
                break;
            case Phone:
                field = new PhoneField();
                break;
            case Number:
                field = new NumberField();
                break;
            case Currency:
                field = new CurrencyField();
                break;
            case Datetime:
                field = new DatetimeField();
                break;
            case LongText:
                field = new LongTextField();
                break;
            case SelectOne:
                field =  new SelectOneField();
                break;
            case Percentage:
                field = new PercentageField();
                break;
            case SelectMany:
                field = new SelectManyField();
                break;
            case MutiLevelSelectOne:
                field = new MutiLevelSelectOneField();
                break;
            default:
                break;
        }
        return field;
    }
}
