/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ObjectApiNameSequenceDAO
 * Author:   lufeiwang
 * Date:   2019/4/18
 */
package com.sn.gz.pmp.dsc.dao.definition;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.definition.ObjectApiNameSequence;
import com.sn.gz.pmp.dsc.mapper.definition.ObjectApiNameSequenceMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * apiName序号递增
 *
 * @author lufeiwang
 * 2019/4/18
 */
@Component
public class ObjectApiNameSequenceDAO extends BaseDAO<ObjectApiNameSequence, Long> {

    private static final Integer INIT_SEQUENCE_VALUE = 1;

    @Resource
    private ObjectApiNameSequenceMapper objectApiNameSequenceMapper;

    @Override
    public BaseMapper<ObjectApiNameSequence> getMapper() {
        return objectApiNameSequenceMapper;
    }

    @Transactional
    public String getApiNameSequence(String objectType, Long groupId) {
        ObjectApiNameSequence objectApiNameSequence = getByObjectType(objectType, groupId);
        if (null == objectApiNameSequence) {
            initSequence(objectType,groupId);
            return INIT_SEQUENCE_VALUE.toString();
        } else {
            increaseSequence(objectApiNameSequence);
        }
        return objectApiNameSequence.getSequence().toString();
    }

    /**
     * 获取序号
     *
     * @param objectType 业务对象类型
     * @return ObjectApiNameSequence
     */
    private ObjectApiNameSequence getByObjectType(String objectType, Long groupId) {
        EntityClassWrapper<ObjectApiNameSequence> ew = new EntityClassWrapper(ObjectApiNameSequence.class);
        ew.eq("objectType", objectType);
        ew.eq("groupId", groupId);
        List<ObjectApiNameSequence> list = objectApiNameSequenceMapper
                .selectList(ew);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 初始化序列
     *
     * @param objectType 业务对象类型
     * @author lufeiwang
     * 2019/4/18
     */
    @Transactional
    public void initSequence(String objectType,Long groupId) {
        ObjectApiNameSequence objectApiNameSequence = new ObjectApiNameSequence();
        objectApiNameSequence.setObjectType(objectType);
        objectApiNameSequence.setGroupId(groupId);
        objectApiNameSequence.setSequence(INIT_SEQUENCE_VALUE + 1);
        objectApiNameSequence.setCreateDatetime(new Date());
        objectApiNameSequence.setModifiedDatetime(new Date());
        save(objectApiNameSequence);
    }

    /**
     * 序号递增
     *
     * @param objectApiNameSequence 业务对象序列
     * @author lufeiwang
     * 2019/4/18
     */
    @Transactional
    public void increaseSequence(ObjectApiNameSequence objectApiNameSequence) {
        ObjectApiNameSequence sequence = new ObjectApiNameSequence();
        sequence.setId(objectApiNameSequence.getId());
        sequence.setObjectType(objectApiNameSequence.getObjectType());
        sequence.setGroupId(objectApiNameSequence.getGroupId());
        sequence.setCreateDatetime(objectApiNameSequence.getCreateDatetime());
        sequence.setModifiedDatetime(new Date());
        sequence.setSequence(objectApiNameSequence.getSequence() + 1);
        updateById(sequence);
    }
}
