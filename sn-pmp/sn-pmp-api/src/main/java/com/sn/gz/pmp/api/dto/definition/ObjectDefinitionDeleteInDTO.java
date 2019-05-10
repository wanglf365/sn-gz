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
public class ObjectDefinitionDeleteInDTO implements Serializable {

    /**
     * 业务对象定义主键
     */
    private Long id;
    /**
     * 字段api名称
     */
    String fieldApiName;
}
