/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: BaseAuthorityMapper
 * Author:   Enma.ai
 * Date:     2019-01-02
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.BaseAuthority;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限字典
 *
 * @author lufeiwang
 * 2019/4/25
 */
public interface BaseAuthorityMapper extends BaseMapper<BaseAuthority> {

    /**
     * 保存或更新用户权限表
     *
     * @param baseAuthority 用户权限表
     * @author xiaoxueliang
     * 2018/8/3
     */
    void saveOrUpdate(@Param("baseAuthority") BaseAuthority baseAuthority);

    /**
     * 批量保存用户权限表
     *
     * @param baseAuthorityList 用户权限表
     * @author xiaoxueliang
     * 2018/8/3
     */
    void batchInsert(@Param("baseAuthorityList") List<BaseAuthority> baseAuthorityList);

    /**
     * 批量保存或更新用户权限表
     *
     * @param baseAuthorityList 用户权限表
     * @author xiaoxueliang
     * 2018/8/3
     */
    void batchSaveOrUpdate(@Param("baseAuthorityList") List<BaseAuthority> baseAuthorityList);
}