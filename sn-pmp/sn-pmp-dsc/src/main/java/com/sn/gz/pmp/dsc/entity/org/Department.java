
package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 部门
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_org_department")
public class Department extends BaseEntity {
    /**
     * 部门名称
     */
    @TableField("name")
    private String name;
    /**
     * 部门描述
     */
    @TableField("description")
    private String description;
    /**
     * id路径，使用逗号分隔，包含自身
     */
    @TableField("path")
    private String path;
    /**
     * 钉钉平台的主键
     */
    @TableField("dingtalk_pk")
    private String dingtalkPK;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 上级部门
     * 根部门parent_id为空
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 部门层级
     * 默认公司为第一层级，
     * 注册时会默认创建level=0
     * 依次+1
     */
    @TableField("level")
    private Integer level;

    public Department() {
    }

    private Department(Builder builder) {
        setId(builder.id);
        setGroupId(builder.groupId);
        setCreateDatetime(builder.createDatetime);
        setModifiedDatetime(builder.modifiedDatetime);
        setName(builder.name);
        setDescription(builder.description);
        setPath(builder.path);
        setSort(builder.sort);
        setParentId(builder.parentId);
        setLevel(builder.level);
        setDingtalkPK(builder.dingtalkPK);
    }

    @SuppressWarnings("unused")
    public static final class Builder {
        private Long id;
        private Long groupId;
        private Date createDatetime;
        private Date modifiedDatetime;
        private String name;
        private String description;
        private String path;
        private Integer sort;
        private Integer level;
        private Long parentId;
        private String dingtalkPK;

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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }
        public Builder dingtalkPK(String dingtalkPK) {
            this.dingtalkPK = dingtalkPK;
            return this;
        }


        public Builder sort(Integer sort) {
            this.sort = sort;
            return this;
        }

        public Builder level(Integer level) {
            this.level = level;
            return this;
        }

        public Builder parentId(Long parentId) {
            this.parentId = parentId;
            return this;
        }

        public Department build() {
            return new Department(this);
        }
    }
}
