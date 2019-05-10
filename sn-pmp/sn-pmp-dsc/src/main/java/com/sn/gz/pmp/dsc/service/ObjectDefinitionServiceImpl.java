/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ObjectDefinitionServiceImpl
 * Author:   lufeiwang
 * Date:   2019/4/18
 */
package com.sn.gz.pmp.dsc.service;

import com.sn.gz.core.sandbox.UserContext;
import com.sn.gz.dscstarter.utils.AnnotationConstants;
import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionDeleteInDTO;
import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionOutDTO;
import com.sn.gz.pmp.api.dto.definition.ObjectDefinitionUpdateInDTO;
import com.sn.gz.pmp.api.inter.ObjectDefinitionService;
import com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition;
import com.sn.gz.pmp.dsc.manager.definition.ObjectDefinitionManager;
import com.sn.gz.pmp.dsc.transfer.DefinitionTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 业务对象定义
 *
 * @author lufeiwang
 * 2019/4/18
 */
@Slf4j
@Component
@Service(version = AnnotationConstants.VERSION, filter = "contextProviderFilter")
public class ObjectDefinitionServiceImpl implements ObjectDefinitionService {

    @Resource
    private ObjectDefinitionManager objectDefinitionManager;

    @Override
    public void update(ObjectDefinitionUpdateInDTO objectDefinitionUpdateInDTO) {
        Long groupId = UserContext.getContextGroupId();
        String objectApiName = objectDefinitionUpdateInDTO.getObjectApiName();
        String fieldJson = objectDefinitionUpdateInDTO.getFieldJson();
        String displayName = objectDefinitionUpdateInDTO.getDisplayName();
        String constraintsJson = objectDefinitionUpdateInDTO.getConstraintsJson();

        objectDefinitionManager.update(objectApiName, fieldJson, displayName,
                constraintsJson, groupId);
    }

    @Override
    public void deleteFiledDefinition(ObjectDefinitionDeleteInDTO objectDefinitionDeleteInDTO) {
        Long id = objectDefinitionDeleteInDTO.getId();
        String fieldApiName = objectDefinitionDeleteInDTO.getFieldApiName();
        objectDefinitionManager.delete(fieldApiName, id);
    }

    @Override
    public ObjectDefinitionOutDTO getByApiName(String objectApiName, Long groupId) {
        ObjectDefinition objectDefinition = objectDefinitionManager.getByApiName(objectApiName, groupId);
        return DefinitionTransfer.INSTANCE.map(objectDefinition);
    }

}
