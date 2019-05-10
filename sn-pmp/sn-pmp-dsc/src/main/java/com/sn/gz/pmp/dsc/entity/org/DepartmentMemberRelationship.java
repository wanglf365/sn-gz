/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: DepartmentCustomer
 * Author:   xiaoxueliang
 * Date:     2018-07-12
 */
package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 成员部门关系表
 *
 * @author lufeiwang
 * 2019/4/24
 */
@SuppressWarnings("unused")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_org_department_member_relationship")
public class DepartmentMemberRelationship extends BaseEntity {
    /**
     * 部门id
     */
    @TableField("department_id")
    private Long departmentId;
    /**
     * 用户id
     */
    @TableField("member_id")
    private Long memberId;
    /**
     * 是否是负责人（1：是，0：否）
     */
    @TableField("leader")
    private String leader;

    /**
     * 是否是主部门（1：是，0：否）
     */
    @TableField("main_department")
    private String mainDepartment;

    public DepartmentMemberRelationship() {
    }

    private DepartmentMemberRelationship(Builder builder) {
        setId(builder.id);
        setGroupId(builder.groupId);
        setCreateDatetime(builder.createDatetime);
        setModifiedDatetime(builder.modifiedDatetime);
        setDepartmentId(builder.departmentId);
        setMemberId(builder.memberId);
        setLeader(builder.leader);
    }

    public static final class Builder {
        private Long id;
        private Long groupId;
        private Date createDatetime;
        private Date modifiedDatetime;
        private Long departmentId;
        private Long memberId;
        private String leader;

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

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder memberId(Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public Builder leader(String leader) {
            this.leader = leader;
            return this;
        }

        public DepartmentMemberRelationship build() {
            return new DepartmentMemberRelationship(this);
        }
    }
}
