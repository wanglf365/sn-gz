package com.sn.gz.pmp.api.dto.definition;

import lombok.Data;

import java.util.Date;

/**
 * 业务对象描述
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
public class ObjectDefinitionOutDTO {

    private Long id;
    /**
     * 集团ID
     */
    private Long groupId;
    /**
     * 业务对象名称
     */
    private String objectApiName;
    /**
     * 自定义数据定义
     */
    private String fields;
    /**
     * 描述
     */
    private String displayName;
    /**
     * 约束
     */
    private String constraints;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 创建时间
     */
    private Date createDatetime;
    /**
     * 修改时间
     */
    private Date modifiedDatetime;

}
