/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ObjectDefinitionService
 * Author:   lufeiwang
 * Date:     2019/4/18
 */
package com.sn.gz.pmp.api.inter;

import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionDeleteInDTO;
import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionOutDTO;
import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionUpdateInDTO;

/**
 * 业务对象定义
 *
 * @author lufeiwang
 * 2019/4/18
 */
public interface ObjectDefinitionService {

    /**
     * 更新业务对象定义
     *
     * @param objectDefinitionUpdateInDTO 入参
     * @author lufeiwang
     * 2019/4/19
     */
    void update(ObjectDefinitionUpdateInDTO objectDefinitionUpdateInDTO);

    /**
     * 删除业务对象字段
     *
     * @param objectDefinitionDeleteInDTO 入参
     * @author lufeiwang
     * 2019/4/19
     */
    void deleteFiledDefinition(ObjectDefinitionDeleteInDTO objectDefinitionDeleteInDTO);

    /**
     * 获取业务对象定义
     *
     * @param objectApiName api名称
     * @param groupId       集团id
     * @return 业务对象
     */
    ObjectDefinitionOutDTO getByApiName(String objectApiName, Long groupId);

}
