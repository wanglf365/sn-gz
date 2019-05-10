/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BaseAuthorityDAO
 * Author:   Enma.ai
 * Date:     2019-01-02
 */
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.pmp.dsc.entity.org.BaseAuthority;
import com.sn.gz.pmp.dsc.mapper.org.BaseAuthorityMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限字典
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Component
public class BaseAuthorityDAO extends BaseDAO<BaseAuthority, Long> {

    @Resource
    private BaseAuthorityMapper baseAuthorityMapper;

    @Override
    public BaseMapper<BaseAuthority> getMapper() {
        return baseAuthorityMapper;
    }

    /**
     * 保存或更新用户权限表
     *
     * @param baseAuthority 用户权限表
     * @author xiaoxueliang
     * 2018/8/6
     */
    public void saveOrUpdate(BaseAuthority baseAuthority) {
        baseAuthorityMapper.saveOrUpdate(baseAuthority);
    }

    /**
     * 批量保存用户权限表
     *
     * @param baseAuthorityList 用户权限表
     * @author xiaoxueliang
     * 2018/8/6
     */
    public void batchSave(List<BaseAuthority> baseAuthorityList) {
        baseAuthorityMapper.batchInsert(baseAuthorityList);
    }

    /**
     * 批量保存或更新用户权限表
     *
     * @param baseAuthorityList 用户权限表
     * @author xiaoxueliang
     * 2018/8/6
     */
    public void batchSaveOrUpdate(List<BaseAuthority> baseAuthorityList) {
        baseAuthorityMapper.batchSaveOrUpdate(baseAuthorityList);
    }


}
