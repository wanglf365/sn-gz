package com.sn.gz.pmp.dsc.definition.field.bo.element;

import com.alibaba.fastjson.JSON;
import com.sn.gz.pmp.dsc.constants.DefinitionConstants;
import com.sn.gz.pmp.dsc.definition.field.FieldType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本字段类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TextField extends FieldBase {
    private int maxLength = 50;
    private String pattern;
    private Boolean participial;


    public static void main(String[] arg) {
        List<FieldBase> list = new ArrayList<>();


        TextField textField = new TextField();
        textField.setMaxLength(55);
        textField.setParticipial(true);
        textField.setPattern("sdfsd");
        textField.setDataType(FieldType.Text.getValue());
        textField.setFieldApiName("name_1");
        textField.setConstrains(new ArrayList());
        textField.setEnable(true);
        textField.setDescription("测试姓名");
        textField.setHelpTip("帮助");
        textField.setLabel("姓名");
        textField.setRequired(true);
        textField.setSystemType(DefinitionConstants.FieldSystemType.SYSTEM);

        list.add(textField);

        String str="[{\"constrains\":[],\"dataType\":\"Text\",\"description\":\"测试姓名\",\"enable\":true,\"fieldApiName\":\"name_1\",\"helpTip\":\"帮助\",\"label\":\"姓名\",\"maxLength\":55,\"participial\":true,\"pattern\":\"sdfsd\",\"required\":true,\"systemType\":\"SYSTEM\"}]";


        System.out.println(JSON.toJSONString(list));

    }
}
