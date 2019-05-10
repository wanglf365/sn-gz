/*
 * Copyright (C), 2018-2019, ShinianData
 * FileName: FieldTest
 * Author:   hanson
 * Date:   2019/4/24
 */
package com.sn.gz.pmp.dsc;

import com.alibaba.fastjson.JSON;
import com.sn.gz.pmp.dsc.constants.DefinitionConstants;
import com.sn.gz.pmp.dsc.definition.field.FieldType;
import com.sn.gz.pmp.dsc.definition.field.FieldUtil;
import com.sn.gz.pmp.dsc.definition.field.bo.element.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanson
 * 2019/4/24
 */
public class FieldTest {
    /**
     * 测试字段列表和json串之间的相互转换
     */
    @Test
    void fieldToJson(){
        List<FieldBase> list = new ArrayList<>();
        List<String>constrains = new ArrayList<>();
        constrains.add("abc");
        constrains.add("def");
        TextField textField = new TextField();
        textField.setMaxLength(55);
        textField.setParticipial(true);
        textField.setPattern("sdfsd");
        textField.setDataType(FieldType.Text.getValue());
        textField.setFieldApiName("name_1");
        textField.setConstrains(constrains);
        textField.setEnable(true);
        textField.setDescription("测试姓名");
        textField.setHelpTip("帮助");
        textField.setLabel("姓名");
        textField.setRequired(true);
        textField.setSystemType(DefinitionConstants.FieldSystemType.SYSTEM);

        list.add(textField);
        DateField dateField = new DateField();
        List<String> constrains2 = new ArrayList<>();
        constrains2.add("123");

        dateField.setDateFormat("yyyyMMdd");
        dateField.setTimeZone("24h");
        dateField.setDataType(FieldType.Date.getValue());
        dateField.setFieldApiName("date_1");
        dateField.setConstrains(constrains2);
        dateField.setEnable(true);
        dateField.setDescription("测试日期");
        dateField.setHelpTip("帮助");
        dateField.setLabel("生日");
        dateField.setRequired(true);
        dateField.setSystemType(DefinitionConstants.FieldSystemType.SYSTEM);
        List<ElementOption> list10 = new ArrayList<>();
        ElementOption eo1= new ElementOption();
        eo1.setLabel("label1");
        eo1.setValue("value1");
        list10.add(eo1);
        eo1= new ElementOption();
        eo1.setLabel("label2");
        eo1.setValue("value2");
        list10.add(eo1);
        SelectManyField smf = new SelectManyField();
        smf.setOptions(list10);
        smf.setDataType(FieldType.SelectMany.getValue());
        smf.setSystemType(DefinitionConstants.FieldSystemType.SYSTEM);
        list.add(dateField);
        list.add(smf);
        String jsonStr=JSON.toJSONString(list);
//        System.out.println(JSON.toJSONString(list));
        List<FieldBase> list2 = JSON.parseArray(jsonStr,FieldBase.class);
        Assert.assertNotEquals(list,list2);
        List<FieldBase> list3= FieldUtil.getFieldList(jsonStr);
        String jsonStr3 = JSON.toJSONString(list3);
        Assert.assertEquals(jsonStr3,jsonStr);
        Assert.assertEquals(list,list3);
//        System.out.println(list3.toString());



    }

}
