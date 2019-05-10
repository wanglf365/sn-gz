/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: User
 * Author:   xiaoxueliang
 * Date:     2018/3/21
 */
package com.sn.gz.pmp.dsc.entity.auth;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 用户实体
 *
 * @author xiaoxueliang
 * @since 2018-03-21
 */
@Data
@ToString(callSuper = true)
@TableName("t_auth_union_user")
@NoArgsConstructor
public class UnionUser{

    /**
     * ID
     */
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;

    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 是否启用（1：启用，0：未启用）
     */
    @TableField("enable")
    private String enable;
    /**
     * 手机
     */
    @TableField("phone")
    private String phone;
    /**
     * 创建时间
     */
    @TableField(value = "create_datetime", fill = FieldFill.INSERT)
    private Date createDatetime;
    /**
     * 修改时间
     */
    @TableField(value = "modified_datetime", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedDatetime;

    private UnionUser(Builder builder) {
        setId(builder.id);
        setPassword(builder.password);
        setCreateDatetime(builder.createDatetime);
        setModifiedDatetime(builder.modifiedDatetime);
        setPhone(builder.phone);
        setEnable(builder.enable);
        setEmail(builder.email);
    }

    public static final class Builder {
        private Long id;

        private String password;

        private String email;

        private String enable;

        private String phone;

        private Date createDatetime;

        private Date modifiedDatetime;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder createDatetime(Date createDatetime) {
            this.createDatetime = createDatetime;
            return this;
        }

        public Builder modifiedDatetime(Date modifiedDatetime) {
            this.modifiedDatetime = modifiedDatetime;
            return this;
        }

        public Builder enable(String enable) {
            this.enable = enable;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UnionUser build() {
            return new UnionUser(this);
        }
    }
}
