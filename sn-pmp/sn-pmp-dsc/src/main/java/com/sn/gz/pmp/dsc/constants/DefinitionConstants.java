/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: FieldConstants
 * Author:   lufeiwang
 * Date:   2019/4/17
 */
package com.sn.gz.pmp.dsc.constants;

/**
 * 常量类
 *
 * @author lufeiwang
 * 2019/4/17
 */
public class DefinitionConstants {

    /**
     * 字段属性
     *
     * @author lufeiwang
     * 2019/4/17
     */
    public enum FieldSystemType {
        SYSTEM("SYSTEM"), CUSTOMIZE("CUSTOMIZE");
        private final String value;

        FieldSystemType(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 初始化业务对象api名字
     */
    public static enum ObjectApiName {
        CUSTOMER("CUSTOMER"), PRODUCT("PRODUCT"), ORDER("ORDER");
        private final String value;

        private ObjectApiName(final String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static final String NOT_EXISTS_FIELD_DATA_ERROR = "未知数据类型";

    public static final String FIELD_DUPLICATION = "字段重复";

    public static final String NOT_EXISTS_FIELD_DESCRIPTION = "字段未定义";

    public final static int OBJECT_DEFINITION_VERSION = 1;

    public static final String INVALID_OBJECT_NAME = "非法对象";
}
