/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ObjectDefinitionUpdateInDTO
 * Author:   lufeiwang
 * Date:   2019/4/18
 */
package com.sn.gz.pmp.api.dto.definition;

import lombok.Data;

import java.io.Serializable;

/**
 * 业务对象定义更新入参
 *
 * @author lufeiwang
 * 2019/4/18
 */
@Data
public class ObjectDefinitionUpdateInDTO implements Serializable {

    /**
     * 业务对象api名称
     */
    String objectApiName;
    /**
     * 列json
     */
    String fieldJson;
    /**
     * 业务对象展示名
     */
    String displayName;
    /**
     * 业务对象约束规则json格式
     */
    String constraintsJson;
}
