/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RoleAuthority
 * Author:   Enma.ai
 * Date:     2019-01-02
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
 *
 *角色权限关联表
 * @author lufeiwang
 * 2019/4/24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_org_role_authority_relationship")
public class RoleAuthorityRelationship extends BaseEntity {
    /**
     * 角色Id
     */
    @TableField("role_id")
    private Long roleId;
    /**
     * 权限id（对应集团权限表主键id）
     */
    @TableField("authority_id")
    private Long authorityId;

    private RoleAuthorityRelationship(Builder builder) {
        setId(builder.id);
        setGroupId(builder.groupId);
        setCreateDatetime(builder.createDatetime);
        setModifiedDatetime(builder.modifiedDatetime);
        setRoleId(builder.roleId);
        setAuthorityId(builder.authorityId);
    }

    public static final class Builder {
        private Long id;
        private Long groupId;
        private Date createDatetime;
        private Date modifiedDatetime;
        private Long roleId;
        private Long authorityId;

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

        public Builder createDatetime(Date createDatetime) {
            this.createDatetime = createDatetime;
            return this;
        }

        public Builder modifiedDatetime(Date modifiedDatetime) {
            this.modifiedDatetime = modifiedDatetime;
            return this;
        }

        public Builder roleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder authorityId(Long authorityId) {
            this.authorityId = authorityId;
            return this;
        }

        public RoleAuthorityRelationship build() {
            return new RoleAuthorityRelationship(this);
        }
    }
}
