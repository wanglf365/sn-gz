/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ObjectDefinitionManager
 * Author:   lufeiwang
 * Date:   2019/4/17
 */
package com.sn.gz.pmp.dsc.manager.definition;

import com.alibaba.fastjson.JSON;
import com.sn.gz.core.BusinessException;
import com.sn.gz.core.utils.IdGenerator;
import com.sn.gz.pmp.dsc.constants.DefinitionConstants;
import com.sn.gz.pmp.dsc.dao.definition.ObjectApiNameSequenceDAO;
import com.sn.gz.pmp.dsc.dao.definition.ObjectDefinitionDAO;
import com.sn.gz.pmp.dsc.definition.MetadataUtil;
import com.sn.gz.pmp.dsc.definition.field.bo.element.FieldBase;
import com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 业务对象定义manager
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Component
@Slf4j
public class ObjectDefinitionManager {

    @Resource
    private ObjectDefinitionDAO objectDefinitionDAO;

    @Resource
    private ObjectApiNameSequenceDAO objectApiNameSequenceDAO;

    /**
     * 业务对象定义写入
     *
     * @param objectApiName   业务对象
     * @param fieldJson       新列
     * @param displayName     展示名称
     * @param constraintsJson 约束
     * @param groupId         集团id
     * @author lufeiwang
     * 2019/4/18
     */
    @Transactional
    public void update(String objectApiName, String fieldJson, String displayName,
                       String constraintsJson, Long groupId) {
        ObjectDefinition objectDefinition = getByApiName(objectApiName, groupId);
        if (null == objectDefinition) {
            objectDefinition = new ObjectDefinition();
            objectDefinition.setObjectApiName(objectApiName);
            objectDefinition.setCreateDatetime(new Date());
            objectDefinition.setModifiedDatetime(new Date());
            objectDefinition.setDisplayName(displayName);
            objectDefinition.setConstraints(constraintsJson);
            objectDefinition.setVersion(DefinitionConstants.OBJECT_DEFINITION_VERSION);
            objectDefinition.setGroupId(groupId);
            objectDefinition.setId(IdGenerator.getInstance().nextId());
            List<FieldBase> fieldList = MetadataUtil.json2FieldList(fieldJson);
            setFieldApiName(fieldList, objectApiName, groupId);
            objectDefinition.setFields(JSON.toJSONString(fieldList));
            add(objectDefinition);
        } else {
            String oldFieldsJson = objectDefinition.getFields();
            objectDefinition.setVersion(objectDefinition.getVersion() + 1);
            objectDefinition.setModifiedDatetime(new Date());
            List<FieldBase> newAddFields = MetadataUtil.json2FieldList(fieldJson);
            setFieldApiName(newAddFields, objectApiName, groupId);
            List<FieldBase> fields = MetadataUtil.json2FieldList(oldFieldsJson);
            fields = MetadataUtil.mergeFieldList(fields, newAddFields);
            MetadataUtil.checkFieldDulplication(fields);
            objectDefinition.setFields(JSON.toJSONString(fields));
            update(objectDefinition);
        }
    }

    /**
     * 删除业务对象定义
     *
     * @param fieldApiName fieldApiName
     * @param id           主键
     * @author lufeiwang
     * 2019/4/17
     */
    @Transactional
    public void delete(String fieldApiName, Long id) {
        ObjectDefinition objectDefinition = getById(id);
        if (null == objectDefinition) {
            return;
        }
        String fieldsJson = objectDefinition.getFields();
        List<FieldBase> newFields = new ArrayList<>();
        List<FieldBase> fields = MetadataUtil.json2FieldList(fieldsJson);

        Iterator<FieldBase> iterator = fields.iterator();
        boolean flag = false;
        while (iterator.hasNext()) {
            if (fieldApiName.equals(iterator.next().getFieldApiName())) {
                flag = true;
                iterator.remove();
            }
        }
        if (flag) {
            objectDefinition.setFields(JSON.toJSONString(newFields));
            update(objectDefinition);
        }
    }

    /**
     * 自动生成filed api名称
     *
     * @param fieldListIn   字段
     * @param objectApiName objectApiName
     * @author lufeiwang
     * 2019/4/17
     */
    private void setFieldApiName(List<FieldBase> fieldListIn, String objectApiName, Long groupId) {
        if (!objectApiName.equals(DefinitionConstants.ObjectApiName.CUSTOMER.getValue())
                && !objectApiName.equals(DefinitionConstants.ObjectApiName.PRODUCT.getValue())
                && !objectApiName.equals(DefinitionConstants.ObjectApiName.ORDER.getValue())) {
            throw new BusinessException(DefinitionConstants.INVALID_OBJECT_NAME);
        }

        if (CollectionUtils.isEmpty(fieldListIn)) {
            return;
        }
        for (FieldBase fieldBase : fieldListIn) {
            if (StringUtils.isEmpty(fieldBase.getFieldApiName())) {
                fieldBase.setFieldApiName(objectApiName + "_" + objectApiNameSequenceDAO.getApiNameSequence(objectApiName, groupId));
            }
        }
    }

    /**
     * 新增业务对象定义
     *
     * @param objectDefinition 业务对象定义
     * @author lufeiwang
     * 2019/4/17
     */
    @Transactional
    public void add(ObjectDefinition objectDefinition) {
        objectDefinitionDAO.save(objectDefinition);
    }

    /**
     * 更新业务对象定义
     *
     * @param objectDefinition 业务对象定义
     * @author lufeiwang
     * 2019/4/17
     */
    @Transactional
    public void update(ObjectDefinition objectDefinition) {
        objectDefinitionDAO.updateById(objectDefinition);
    }

    /**
     * 根据apiName查询业务对象定义
     *
     * @param objectApiName objectApiName
     * @param groupId       集团id
     * @return com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition
     * @author lufeiwang
     * 2019/4/17
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ObjectDefinition getByApiName(String objectApiName, Long groupId) {
        return objectDefinitionDAO.getByApiName(objectApiName, groupId);
    }

    /**
     * 根据id查询业务对象定义
     *
     * @param id 主键
     * @return com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition
     * @author lufeiwang
     * 2019/4/17
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ObjectDefinition getById(Long id) {
        return objectDefinitionDAO.selectById(id);
    }

    /**
     * 根据apiName查询业务对象定义
     *
     * @param objectApiName objectApiName
     * @param fieldApiName  fieldApiName
     * @param groupId       集团id
     * @return com.sn.gz.pmp.dsc.definition.field.bo.element.FieldBase
     * @author lufeiwang
     * 2019/4/17
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public FieldBase getFieldDefinition(String objectApiName, String fieldApiName, Long groupId) {
        ObjectDefinition objectDefinition = objectDefinitionDAO
                .getByApiName(objectApiName, groupId);
        if (null == objectDefinition) {
            return null;
        } else {
            String fieldJson = objectDefinition.getFields();
            List<FieldBase> fieldList = MetadataUtil.json2FieldList(fieldJson);
            for (FieldBase fieldDescribeBase : fieldList) {
                if (fieldApiName.equals(fieldDescribeBase.getFieldApiName())) {
                    return fieldDescribeBase;
                }
            }
        }
        return null;
    }

}
