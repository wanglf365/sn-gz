/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Customer
 * Author:   Enma.ai
 * Date:     2018/3/27
 */
package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * customer表entity
 *
 * @author Enma.ai
 * 2018/3/27
 */
@Data
@TableName("t_org_member")
public class Member extends BaseEntity {

    /**
     * 全局用户id
     */
    @TableField(value = "union_id")
    private Long unionId;
    /**
     * 用户名
     */
    @TableField("name")
    private String name;
    /**
     * 是否启用
     */
    @TableField("enable")
    private String enable;
    /**
     * 手机号码
     */
    @TableField("phone")
    private String phone;
    /**
     * 钉钉平台的主键userId
     */
    @TableField("dingtalk_pk")
    private String dingtalkPK;

    /**
     * 钉钉平台的unionid
     */
    @TableField("dingtalk_unionId")
    private String dingtalkUnionId;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 用户头像链接
     */
    @TableField("profile_photo")
    private String profilePhoto;

    public Member() {
    }

    private Member(Builder builder) {
        setId(builder.id);
        setGroupId(builder.groupId);
        setCreateDatetime(builder.createDatetime);
        setModifiedDatetime(builder.modifiedDatetime);
        setName(builder.name);
        setPhone(builder.phone);
        setDingtalkPK(builder.dingtalkPK);
        setDingtalkUnionId(builder.dingtalkUnionId);
        setEnable(builder.enable);
        setEmail(builder.email);
        setUnionId(builder.unionId);
    }

    public static final class Builder {
        private Long id;
        private Long groupId;
        private Date createDatetime;
        private Date modifiedDatetime;
        private String name;
        private String enable;
        private String phone;
        private String dingtalkUnionId;
        private String dingtalkPK;
        private String email;
        private Long unionId;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder groupId(Long groupId) {
            this.groupId = groupId;
            return this;
        }
        public Builder unionId(Long unionId) {
            this.unionId = unionId;
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

        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder dingtalkUnionId(String dingtalkUnionId) {
            this.dingtalkUnionId = dingtalkUnionId;
            return this;
        }

        public Builder enable(String enable) {
            this.enable = enable;
            return this;
        }

        public Builder dingtalkPK(String dingtalkPK) {
            this.dingtalkPK = dingtalkPK;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
