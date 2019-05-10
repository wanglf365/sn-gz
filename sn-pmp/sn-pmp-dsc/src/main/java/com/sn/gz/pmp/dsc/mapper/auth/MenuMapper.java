package com.sn.gz.pmp.dsc.mapper.auth;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.pmp.dsc.entity.auth.Menu;

import java.util.List;

/**
 *
 *菜单权限表
 * @author lufeiwang
 * 2019/5/8
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     *
     *获取一级菜单
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.Menu>
     * @author lufeiwang
     * 2019/5/8
     */
    List<Menu> listFirstClassMenuList();
}