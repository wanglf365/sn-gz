/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: UserMapper
 * Author:   xiaoxueliang
 * Date:     2018/3/22
 */
package com.sn.gz.pmp.dsc.mapper.auth;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 全局用户
 *
 * @author lufeiwang
 * 2019/4/25
 */
public interface UnionUserMapper extends BaseMapper<UnionUser> {

    /**
     * 批量保存用户
     *
     * @param userList 用户信息集合
     * @return java.lang.Integer
     * @author xiaoxueliang
     * 2018/5/4
     */
    Integer batchInsert(@Param("unionUserList") List<UnionUser> userList);
}
