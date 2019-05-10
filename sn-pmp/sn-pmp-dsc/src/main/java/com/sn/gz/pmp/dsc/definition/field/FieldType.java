package com.sn.gz.pmp.dsc.definition.field;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型
 *
 * @author lufeiwang
 * 2019/4/17
 */
public enum FieldType {

    Text("Text"), LongText("LongText"), Number("Number"), SelectOne(
            "SelectOne"), MutiLevelSelectOne(
            "MutiLevelSelectOne"), SelectMany("SelectMany"), Date("Date"), Phone("Phone"), Email(
            "Email"), Image(
            "Image"), URL("URL"), Boolean("Boolean"), Currency("Currency"), Datetime("Datetime"), Time(
            "Time"), File("File"), Percentage("Percentage"), Long("Long");
    private final String value;

    private FieldType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final Map<String, FieldType> stringToEnum = new HashMap<String, FieldType>();

    static {
        for (FieldType type : values()) {
            stringToEnum.put(type.toString(), type);
        }
    }

    public static FieldType fromString(String symbol) {
        return stringToEnum.get(symbol);
    }
}
