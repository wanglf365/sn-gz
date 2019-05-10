/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ObjectDefinitionDao
 * Author:   lufeiwang
 * Date:   2019/4/17
 */
package com.sn.gz.pmp.dsc.dao.definition;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition;
import com.sn.gz.pmp.dsc.mapper.definition.ObjectDefinitionMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务对象定义Dao
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Component
public class ObjectDefinitionDAO extends BaseDAO<ObjectDefinition, Long> {

    @Resource
    private ObjectDefinitionMapper objectDefinitionMapper;

    @Override
    public BaseMapper<ObjectDefinition> getMapper() {
        return objectDefinitionMapper;
    }

    /**
     * 根据apiName查询元数据描述
     *
     * @param objectApiName objectApiName
     * @param groupId       集团id
     * @return com.sn.gz.pmp.dsc.entity.definition.ObjectDefinition
     * @author lufeiwang
     * 2019/4/17
     */
    public ObjectDefinition getByApiName(String objectApiName, Long groupId) {
        EntityClassWrapper<ObjectDefinition> ew = new EntityClassWrapper(ObjectDefinition.class);
        ew.eq("objectApiName", objectApiName);
        ew.eq("groupId", groupId);
        List<ObjectDefinition> list = objectDefinitionMapper
                .selectList(ew);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
