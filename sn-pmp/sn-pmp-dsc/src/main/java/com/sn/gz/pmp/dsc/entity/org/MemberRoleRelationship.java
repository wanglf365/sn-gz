/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: CustomerRole
 * Author:   Enma.ai
 * Date:     2018-08-27
 */
package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 客户角色关系表entity
 *
 * @author Enma.ai
 * 2018-08-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_org_member_role_relationship")
public class MemberRoleRelationship extends BaseEntity {
    /**
     * 成员Id
     */
    @TableField("member_id")
    private Long memberId;
    /**
     * 角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 项目id（人员的项目权限单独制定）
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * GROUP-集团,PROJECT-项目
     */
    @TableField("role_type")
    private String roleType;

    private MemberRoleRelationship(Builder builder) {
        setId(builder.id);
        setGroupId(builder.groupId);
        setCreateDatetime(builder.createDatetime);
        setModifiedDatetime(builder.modifiedDatetime);
        setMemberId(builder.memberId);
        setRoleId(builder.roleId);
        setProjectId(builder.projectId);
    }

    public static final class Builder {
        private Long id;
        private Long groupId;
        private Date createDatetime;
        private Date modifiedDatetime;
        private Long memberId;
        private Long roleId;
        private Long projectId;

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

        public Builder projectId(Long projectId) {
            this.projectId = projectId;
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

        public Builder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder roleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public MemberRoleRelationship build() {
            return new MemberRoleRelationship(this);
        }
    }
}
