package com.sn.gz.pmp.dsc.definition.field.bo.element;

import com.alibaba.fastjson.annotation.JSONField;
import com.sn.gz.pmp.dsc.constants.DefinitionConstants;
import lombok.Data;

import java.util.List;
/**
 * 字段基类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@SuppressWarnings("unused")
@Data
public class FieldBase {
    /**
     *  数据类型转换成json时的名字，统一维护，便于json反向解析时保持一致，不出错
     */
    static public final String DATA_TYPE = "dataType";

    public FieldBase() {
        super();
    }

    /**
     * 系统属性
     */
    private DefinitionConstants.FieldSystemType systemType;
    /**
     * 数据库字段名称
     */
    private String fieldApiName;
    /**
     * 数据类型
     */
    @JSONField(name=DATA_TYPE)
    private String dataType;
    /**
     * 是否必填
     */
    private Boolean required;
    /**
     * 描述
     */
    private String description;
    /**
     * 帮助描述
     */
    private String helpTip;
    /**
     * 前端展示名称
     */
    private String label;
    /**
     * 业务验证逻辑表达式
     */
    private List<String> constrains;
    /**
     * 示例permission:10表示对内部员工可见，外部客户不可见
     * 11表示对内部员工可见，外部客户可见
     * 01表示对内部员工不可见，外部客户可见
     */
    private String permission;
    /**
     * 是否启用
     */
    private Boolean enable;


    private FieldBase(Builder builder) {
        this.systemType = builder.systemType;
        this.fieldApiName = builder.fieldApiName;
        this.dataType = builder.dataType;
        this.required = builder.required;
        this.description = builder.description;
        this.helpTip = builder.helpTip;
        this.label = builder.label;
        this.constrains = builder.constrains;
        this.permission = builder.permission;
        this.enable = builder.enable;
    }

    public static class Builder {
        private DefinitionConstants.FieldSystemType systemType;
        private String fieldApiName;
        private String dataType;
        private Boolean required;
        private String description;
        private String helpTip;
        private String label;
        private List<String> constrains;
        private String permission;
        private Boolean enable;

        public Builder(DefinitionConstants.FieldSystemType systemType) {
            this.systemType = systemType;
        }

        public Builder dataType(String dataType) {
            this.dataType = dataType;
            return this;
        }

        public Builder fieldApiName(String fieldApiName) {
            this.fieldApiName = fieldApiName;
            return this;
        }

        public Builder required(Boolean required) {
            this.required = required;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder helpTip(String helpTip) {
            this.helpTip = helpTip;
            return this;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder constrains(List<String> constrains) {
            this.constrains = constrains;
            return this;
        }

        public Builder timestamp(String permission) {
            this.permission = permission;
            return this;
        }

        public Builder enable(Boolean enable) {
            this.enable = enable;
            return this;
        }

        public FieldBase build() {
            return new FieldBase(this);
        }
    }

}
