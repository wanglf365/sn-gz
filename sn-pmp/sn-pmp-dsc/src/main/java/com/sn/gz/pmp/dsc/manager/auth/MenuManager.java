/*
 * Copyright (C), 2018-2019, 深圳拾年技术有限公司
 * FileName: MenuManager
 * Author:   lufeiwang
 * Date:   2019/5/8
 */
package com.sn.gz.pmp.dsc.manager.auth;

import com.sn.gz.core.BusinessException;
import com.sn.gz.pmp.dsc.dao.auth.MenuDAO;
import com.sn.gz.pmp.dsc.entity.auth.Menu;
import com.sn.gz.pmp.dsc.manager.org.OrgManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单
 *
 * @author lufeiwang
 * 2019/5/8
 */
@Component
@Slf4j
public class MenuManager {

    @Resource
    private OrgManager orgManager;

    @Resource
    private MenuDAO menuDAO;


    private static final Integer SIZE_ONE = 1;
    private static final Integer SIZE_TWO = 2;
    private static final Integer SIZE_THREE = 3;

    /**
     * 集团
     */
    private static final String GROUP = "GROUP";
    /**
     * 公共
     */
    public static final String PUBLIC = "PUBLIC";

    /**
     * 菜单的map key
     */
    public static final String MENU_TYPE_KEY = "menuType";

    /**
     * 项目
     */
    private static final String PROJECT = "PROJECT";
    /**
     * 加载集团和公共权限
     */
    private static final String PERMISSION_GROUP_AND_PUBLIC = "GROUP,PUBLIC";

    public static final String PERMISSION_PUBLIC_AND_PROJECT = "PUBLIC,PROJECT";
    /**
     * 权限map key
     */
    public static final String PERMISSION_TYPE_KEY = "permissionType";

    /**
     * 查询
     *
     * @param permissionType 权限类型
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.Menu>
     * @author lufeiwang
     * 2019/5/8
     */
    public List<Menu> listByPermissionType(String permissionType)
    {
       return menuDAO.listByPermissionType(permissionType);
    }

    public Map<String, String> getMenuAndPermissionType(Long memberId, Long groupId) throws BusinessException {
        Map<String, String> map = new HashMap<>(16);
        //默认集团
        //用户权限类型 目前只有三种
        //判断用户登录时拥有的权限，进入菜单情况：
        //1、集团、公共：集团
        //2、集团、项目：集团
        //3、公共、项目：项目
        //4、集团、公共、项目：集团
        //5、公共：集团
        //6 项目:项目
        List<String> permissionTypeList = orgManager.listMemberPermissionType(memberId, groupId);
        Map<String, String> permissionTypeListMap = new HashMap<>(16);
        for (String type : permissionTypeList) {
            permissionTypeListMap.put(type, "");
        }
        if (permissionTypeListMap.size() == SIZE_THREE) {
            map.put(MENU_TYPE_KEY, GROUP);
            map.put(PERMISSION_TYPE_KEY, PERMISSION_GROUP_AND_PUBLIC);
        }
        if (permissionTypeListMap.size() == SIZE_TWO) {
            if (permissionTypeListMap.get(GROUP) != null && permissionTypeListMap.get(PUBLIC) != null) {
                map.put(MENU_TYPE_KEY, GROUP);
                map.put(PERMISSION_TYPE_KEY, PERMISSION_GROUP_AND_PUBLIC);
            }
            if (permissionTypeListMap.get(GROUP) != null && permissionTypeListMap.get(PROJECT) != null) {
                map.put(MENU_TYPE_KEY, GROUP);
                map.put(PERMISSION_TYPE_KEY, PERMISSION_GROUP_AND_PUBLIC);
            }
            if (permissionTypeListMap.get(PUBLIC) != null && permissionTypeListMap.get(PROJECT) != null) {
                map.put(MENU_TYPE_KEY, PROJECT);
                map.put(PERMISSION_TYPE_KEY, PERMISSION_PUBLIC_AND_PROJECT);
            }
        }
        if (permissionTypeListMap.size() == SIZE_ONE) {
            if (permissionTypeListMap.get(PUBLIC) != null) {
                map.put(MENU_TYPE_KEY, GROUP);
                map.put(PERMISSION_TYPE_KEY, PERMISSION_GROUP_AND_PUBLIC);
            }
            if (permissionTypeListMap.get(PROJECT) != null) {
                map.put(MENU_TYPE_KEY, PROJECT);
                map.put(PERMISSION_TYPE_KEY, PERMISSION_PUBLIC_AND_PROJECT);
            }
            if (permissionTypeListMap.get(GROUP) != null) {
                map.put(MENU_TYPE_KEY, GROUP);
                map.put(PERMISSION_TYPE_KEY, PERMISSION_GROUP_AND_PUBLIC);
            }
        }
        return map;
    }
}
