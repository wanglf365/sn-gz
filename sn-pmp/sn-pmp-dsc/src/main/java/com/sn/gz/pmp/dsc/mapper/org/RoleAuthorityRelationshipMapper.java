/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RoleAuthorityMapper
 * Author:   Enma.ai
 * Date:     2019-01-02
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.org.RoleAuthorityRelationship;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色权限关联表-Mapper
 *
 * @author Enma.ai
 * 2019-01-02
 */
public interface RoleAuthorityRelationshipMapper extends BaseMapper<RoleAuthorityRelationship> {

    /**
     * 批量保存
     *
     * @param roleAuthorityList 角色权限关系
     * @author lufeiwang
     * 2019/4/25
     */
    void batchInsert(@Param("roleAuthorityList") List<RoleAuthorityRelationship> roleAuthorityList);
}