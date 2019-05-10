package com.sn.gz.pmp.dsc.entity.definition;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;

/**
 * 业务对象描述
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@TableName("t_meta_object_definition")
public class ObjectDefinition extends BaseEntity {

    /**
     * 业务对象名称
     */
    @TableField("object_api_name")
    private String objectApiName;
    /**
     * 自定义数据定义
     */
    @TableField("fields")
    private String fields;
    /**
     * 描述
     */
    @TableField("displayName")
    private String displayName;
    /**
     * 约束
     */
    @TableField("constraints")
    private String constraints;
    /**
     * 版本号
     */
    @TableField("version")
    private Integer version;
    /**
     * 上次更新人
     */
    @TableField("lastModifiedBy")
    private Long lastModifiedBy;
    /**
     * 创建人
     */
    @TableField("createdBy")
    private Long createdBy;

}
