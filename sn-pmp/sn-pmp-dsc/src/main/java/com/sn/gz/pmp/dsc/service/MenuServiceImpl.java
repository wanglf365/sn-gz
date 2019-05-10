/*
 * Copyright (C), 2018-2019, 深圳拾年技术有限公司
 * FileName: MenuServiceImpl
 * Author:   lufeiwang
 * Date:   2019/5/8
 */
package com.sn.gz.pmp.dsc.service;

import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.core.BusinessException;
import com.sn.gz.core.sandbox.UserContext;
import com.sn.gz.dscstarter.utils.AnnotationConstants;
import com.sn.gz.pmp.api.dto.auth.MenuOutDTO;
import com.sn.gz.pmp.api.inter.MenuService;
import com.sn.gz.pmp.dsc.constants.ErrorMessage;
import com.sn.gz.pmp.dsc.dao.auth.AuthRedisDAO;
import com.sn.gz.pmp.dsc.entity.auth.Menu;
import com.sn.gz.pmp.dsc.manager.auth.AuthManager;
import com.sn.gz.pmp.dsc.manager.auth.MenuManager;
import com.sn.gz.pmp.dsc.transfer.OrgTransfer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 菜单
 *
 * @author lufeiwang
 * 2019/5/8
 */
@Slf4j
@Component
@Service(version = AnnotationConstants.VERSION, filter = "contextProviderFilter", timeout = 20000)
public class MenuServiceImpl implements MenuService {

    @Resource
    private AuthManager authManager;

    @Resource
    private MenuManager menuManager;

    @Override
    public void checkOperationPermission(List<String> permissionList) throws BusinessException {
        Long groupId = UserContext.getContextGroupId();
        Long memberId = UserContext.getContextMemberId();

        List<String> keyList = new ArrayList<>(permissionList);
        keyList.add(AuthRedisDAO.ACCOUNT_AUTH_MEMBER_PERMS_RELOAD);

        boolean hasPermission = false;
        List<String> values = authManager.getMemberPermissions(groupId, memberId, keyList);
        String reloadValue = values.get(values.size() - 1);
        for (int i = 0; i < values.size() - 1; i++) {
            String value = values.get(i);
            if (StringUtils.isNotBlank(value)) {
                hasPermission = true;
                break;
            }
        }

        if (hasPermission) {
            return;
        }

        if (StringUtils.isNotBlank(reloadValue)) {
            throw new BusinessException(ErrorMessage.NO_PERMISSION);
        }

        authManager.reloadUserOperationPermission(groupId, memberId);
        List<String> reloadValues = authManager.getMemberPermissions(groupId, memberId, keyList);
        for (int i = 0; i < reloadValues.size() - 1; i++) {
            String value = reloadValues.get(i);
            if (StringUtils.isNotBlank(value)) {
                hasPermission = true;
                break;
            }
        }

        if (!hasPermission) {
            throw new BusinessException(ErrorMessage.NO_PERMISSION);
        }
    }

    @Override
    public List<MenuOutDTO> listMenu(String menuType) {
        Long groupId = UserContext.getContextGroupId();
        Long memberId = UserContext.getContextMemberId();

        String permissionType;
        if (StringUtils.isEmpty(menuType)) {
            Map<String, String> permissionTypeMap = menuManager.getMenuAndPermissionType(memberId, groupId);
            if (permissionTypeMap.isEmpty()) {
                throw new BusinessException(ErrorMessage.CUSTOMER_NOT_HAVE_AUTH);
            }
            permissionType = permissionTypeMap.get(MenuManager.PERMISSION_TYPE_KEY);
        } else {
            permissionType = MenuManager.PERMISSION_PUBLIC_AND_PROJECT;
        }
        List<String> authorityList = authManager.listMemberAuthorities(memberId, groupId, permissionType);
        //用户所拥有的权限
        Map<String, String> authorityListMap = new HashMap<>(16);
        for (String authority : authorityList) {
            authorityListMap.put(authority, "");
        }

        List<Menu> menuList = menuManager.listByPermissionType(permissionType);
        List<MenuOutDTO> menuOutDTOList = OrgTransfer.INSTANCE.mapMenuOutDTOList(menuList);
        Map<String, String> displayMenuMap = new LinkedHashMap<>();

        for (MenuOutDTO menuOutDTO : menuOutDTOList) {
            //非叶子节点不处理
            if (!OrgConstants.MENU_IS_LEAF.equals(menuOutDTO.getLeaf())) {
                continue;
            }
            //1、放行的
            if (OrgConstants.MENU_ACCESS_ALLOW.equals(menuOutDTO.getAccessAllow())) {
                //不需要权限控制的菜单
                displayMenuMap.put(menuOutDTO.getId().toString(), "");
                fillPathMenu(displayMenuMap, menuOutDTO.getPath());
                continue;
            }

            //2、处理不放行的
            String authorityString = menuOutDTO.getAuthority();
            if (StringUtils.isBlank(authorityString)) {
                displayMenuMap.put(menuOutDTO.getId().toString(), "");
                //处理父节点
                fillPathMenu(displayMenuMap, menuOutDTO.getPath());
                continue;
            }
            String[] authorityArray = authorityString.split(Menu.SEPARATE);
            for (String authority : authorityArray) {
                String value = authorityListMap.get(authority);
                if (value != null) {
                    //有权限
                    displayMenuMap.put(menuOutDTO.getId().toString(), "");
                    //处理父节点
                    fillPathMenu(displayMenuMap, menuOutDTO.getPath());
                    break;
                }
            }
        }
        //获取所有需要显示的菜单dto对象
        List<MenuOutDTO> displayMenuOutDTOList = new ArrayList<>();
        for (MenuOutDTO menuOutDTO : menuOutDTOList) {
            String idString = menuOutDTO.getId().toString();
            if (displayMenuMap.get(idString) != null) {
                displayMenuOutDTOList.add(menuOutDTO);
            }
        }
        List<MenuOutDTO> menuOutDTOTreeList;
        try {
            menuOutDTOTreeList = transferTree(displayMenuOutDTOList);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        return menuOutDTOTreeList;
    }

    /**
     * 将父节点加入显示列表
     *
     * @param displayMenuMap map集合
     * @param path           路径
     * @author xiaoxueliang
     * 2018/4/17
     */
    private void fillPathMenu(Map<String, String> displayMenuMap, String path) {
        //有父节点
        if (StringUtils.isNotBlank(path)) {
            String[] pathArray = path.split(Menu.SEPARATE);
            for (String id : pathArray) {
                displayMenuMap.put(id, "");
            }
        }
    }

    /**
     * 转换为树形结构
     *
     * @param tree 集合
     * @return 树形集合
     * @throws Exception 异常
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> transferTree(List<T> tree) throws Exception {
        if (tree != null) {
            List treeList = new ArrayList<>();
            Map<Long, Object> map = new LinkedHashMap<>();
            for (Object o : tree) {
                Class clazz = o.getClass();
                Field id = clazz.getDeclaredField("id");
                if (!id.isAccessible()) {
                    id.setAccessible(true);
                }
                Long lId = (Long) id.get(o);
                map.put(lId, o);
            }
            for (Object o : map.keySet()) {

                Long cId = (Long) o;
                Object obj = map.get(cId);
                Class clazz = obj.getClass();
                Field pId = clazz.getDeclaredField("parentId");
                if (!pId.isAccessible()) {
                    pId.setAccessible(true);
                }
                Long id = (Long) pId.get(obj);
                if (id == null) {
                    treeList.add(obj);
                } else {
                    Object object = map.get(id);
                    Class clazz1 = object.getClass();
                    Field children = clazz1.getDeclaredField("children");
                    if (!children.isAccessible()) {
                        children.setAccessible(true);
                    }
                    List list = (List) children.get(object);
                    if (CollectionUtils.isEmpty(list)) {
                        list = new ArrayList();
                    }
                    list.add(obj);
                    children.set(object, list);
                }
            }
            return treeList;
        }
        return null;
    }
}
