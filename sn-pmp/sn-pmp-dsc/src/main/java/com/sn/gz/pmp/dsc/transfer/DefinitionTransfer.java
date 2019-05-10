/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DefinitionTransfer
 * Author:   lufeiwang
 * Date:     2019/4/18
 */
package com.sn.gz.pmp.dsc.transfer;

import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionOutDTO;
import com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数值转化
 *
 * @author lufeiwang
 * 2019/4/18
 */
@Mapper
public interface DefinitionTransfer {

    DefinitionTransfer INSTANCE = Mappers.getMapper(DefinitionTransfer.class);

    ObjectDefinitionOutDTO map(ObjectDefinition objectDefinition);
}
