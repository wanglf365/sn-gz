/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: MenuDao
 * Author:   xiaoxueliang
 * Date:     2018/4/17
 */
package com.sn.gz.pmp.dsc.dao.auth;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.pmp.dsc.entity.auth.Menu;
import com.sn.gz.pmp.dsc.mapper.auth.MenuMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单dao
 *
 * @author xiaoxueliang
 * 2018/4/17
 */
@Component
public class MenuDAO extends BaseDAO<Menu, Long> {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public BaseMapper<Menu> getMapper() {
        return menuMapper;
    }

    /**
     * 获取所有菜单
     *
     * @param ew 条件
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.Menu>
     * @author lufeiwang
     * 2019/5/8
     */
    public List<Menu> getAll(EntityWrapper<Menu> ew) {
        return menuMapper.selectList(ew);
    }

    /**
     * 获取所有二级菜单
     *
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.Menu>
     * @author lufeiwang
     * 2019/5/8
     */
    public List<Menu> getLeafMenu() {
        EntityClassWrapper<Menu> ew = new EntityClassWrapper<>(Menu.class);
        ew.eq("leaf", OrgConstants.MENU_IS_LEAF);
        ew.orderBy("sort", true);
        return menuMapper.selectList(ew);
    }

    /**
     * 查询
     *
     * @param permissionType 权限类型
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.Menu>
     * @author lufeiwang
     * 2019/5/8
     */
    public List<Menu> listByPermissionType(String permissionType) {
        String[] menuArray = permissionType.split(Menu.SEPARATE);
        EntityClassWrapper<Menu> ew = new EntityClassWrapper<>(Menu.class);
        ew.orderBy("sort", true);
        ew.in("menuType", menuArray);
        ew.eq("display", OrgConstants.MenuDisplay.YES.getValue());
        return menuMapper.selectList(ew);
    }

    /**
     * 获取所有一级菜单
     *
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.Menu>
     * @author lufeiwang
     * 2019/5/8
     */
    public List<Menu> listFirstClassMenuList() {
        return menuMapper.listFirstClassMenuList();
    }
}
