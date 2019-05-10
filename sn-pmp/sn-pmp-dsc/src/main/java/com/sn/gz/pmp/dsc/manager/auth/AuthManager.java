/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: AuthManager
 * Author:   lufeiwang
 * Date:   2019/4/16
 */
package com.sn.gz.pmp.dsc.manager.auth;

import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.core.BusinessException;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.core.utils.NumberUtils;
import com.sn.gz.jdbc.starter.query.Filter;
import com.sn.gz.pmp.dsc.constants.ErrorMessage;
import com.sn.gz.pmp.dsc.dao.auth.AuthRedisDAO;
import com.sn.gz.pmp.dsc.dao.auth.UnionUserDAO;
import com.sn.gz.pmp.dsc.dao.org.AuthorityDAO;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.Authority;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权
 *
 * @author lufeiwang
 * 2019/4/16
 */
@Component
@Slf4j
public class AuthManager {

    private static final String GRAPH_CODE_KEY = "USER.GRAPH.CODE.%s";


    @Resource
    private AuthRedisDAO authRedisDAO;

    @Resource
    private AuthorityDAO authorityDAO;

    @Resource
    private UnionUserDAO unionUserDAO;


    /**
     * 通过手机获取用户账户
     *
     * @param phone 手机
     * @return com.sn.gz.pmp.dsc.entity.auth.UnionUser
     * @author lufeiwang
     * 2019/5/8
     */
    public UnionUser getUserByPhone(String phone) {
        List<Filter> filterList = Filter.build()
                .add(Filter.eq("phone", phone))
                .build();

        return unionUserDAO.selectOne(filterList);
    }

    /**
     * 校验验证码
     *
     * @param graphCode 验证码
     * @param key       key
     * @author lufeiwang
     * 2019/5/8
     */
    public void graphCodeVerification(String graphCode, String key) throws BusinessException {
        String value = getGraphCode(key);
        if (StringUtils.isEmpty(value) || !value.equalsIgnoreCase(graphCode)) {
            deleteGraphCode(key);
            throw new BusinessException(ErrorMessage.GRAPH_CODE_ERROR);
        }
        if (!StringUtils.isEmpty(value)) {
            deleteGraphCode(key);
        }
    }

    /**
     * 从缓存中获取用户jwt标识
     *
     * @param userId 用户id
     * @return boolean 存在返回true
     * @author xiaoxueliang
     * 2018/7/2
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean isJwtExistCache(Long userId) throws BusinessException {
        return authRedisDAO.isJwtExistCache(userId);
    }

    /**
     * 刷新用户权限在redis中的过期时间（1小时过期）
     *
     * @param userId 用户id
     * @author xiaoxueliang
     * 2018/3/26
     */
    @Transactional(rollbackFor = Exception.class)
    public void refreshJwt(Long userId) {
        authRedisDAO.refreshJwt(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveGraphCode(String key, String value) {
        authRedisDAO.saveKeyValue(String.format(GRAPH_CODE_KEY, key), value, 60, TimeUnit.MINUTES);
    }

    /**
     * 保存定时器
     *
     * @param termType 终端类型
     * @param id       用户id
     * @author Enma.ai
     * 2018/3/27
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveJWT(String termType, Long id, int time, TimeUnit timeUnit) {
        authRedisDAO.saveJWT(termType, id, time, timeUnit);
    }

    /**
     * 批量获取权限值
     *
     * @param memberId    成员id
     * @param permissions 权限key集合
     * @return java.util.List&lt;java.lang.String&gt;
     * @author xiaoxueliang
     * 2018/6/6
     */
    public List<String> getMemberPermissions(Long groupId, Long memberId, List<String> permissions) {
        return authRedisDAO.getMemberPermissions(groupId, memberId, permissions);
    }

    /**
     * 重新加载用户权限，同时标记为已重新加载过
     *
     * @param groupId  集团id
     * @param memberId 用户id
     * @author xiaoxueliang
     * 2018/6/5
     */
    @Transactional(rollbackFor = Exception.class)
    public void reloadUserOperationPermission(Long groupId, Long memberId) {
        List<String> list = listMemberAuthorities(memberId, groupId, OrgConstants.PERMISSION_ALL);
        //将权限存入redis
        authRedisDAO.reloadOperationPermission(list, groupId, memberId);
        log.info("重新加载成员id={}权限", memberId);
    }

    /**
     * 从数据库中获取用户所有权限（如果有管理员权限则获取公司所有权限）
     *
     * @param customerId     用户id
     * @param groupId        集团id
     * @param permissionType 权限类型（集团、项目、公共）
     * @return java.util.List&lt;java.lang.String&gt;
     * @author xiaoxueliang
     * 2018/3/31
     */
    @Transactional(readOnly = true)
    public List<String> listMemberAuthorities(Long customerId, Long groupId, String permissionType) {
        List<Authority> memberAuthorityList = authorityDAO.listMemberAuthorities(customerId, groupId, permissionType);
        List<Authority> groupAuthorityList = authorityDAO.listGroupAuthorities(groupId);

        if (ListUtils.isNull(memberAuthorityList)) {
            return null;
        }

        if (ListUtils.isNull(groupAuthorityList)) {
            return ListUtils.list2List(memberAuthorityList, "getValue", Authority.class);
        }

        Map<Long, Authority> groupAuthorityMap = ListUtils.list2Map(groupAuthorityList, "getAuthId", Authority.class);
        Map<Long, Authority> memberAuthorityMap = ListUtils.list2Map(memberAuthorityList, "getAuthId", Authority.class);

        for(Authority authority:memberAuthorityList)
        {
            setParentAuthority(groupAuthorityMap,memberAuthorityMap,authority);
        }

        List<String> authorityList = new ArrayList<>();

        for (Map.Entry<Long, Authority> entry : memberAuthorityMap.entrySet()) {
            authorityList.add(entry.getValue().getValue());
        }

        return authorityList;
    }

    /**
     * 设置父权限节点
     *
     * @param groupAuthorityMap  集团
     * @param memberAuthorityMap 个人
     * @param authority          当前权限
     * @author lufeiwang
     * 2019/5/9
     */
    private void setParentAuthority(Map<Long, Authority> groupAuthorityMap, Map<Long, Authority> memberAuthorityMap, Authority authority) {
        if (!NumberUtils.isNullOrZero(authority.getAuthId())) {
            Authority parent = groupAuthorityMap.get(authority.getAuthId());
            if (null != parent && !memberAuthorityMap.containsKey(parent.getAuthId())) {
                memberAuthorityMap.put(parent.getAuthId(), parent);
                setParentAuthority(groupAuthorityMap, memberAuthorityMap, parent);
            }
        }
    }

    /**
     * 获取图片验证码
     *
     * @param key key
     * @return java.lang.String
     * @author Enma.ai
     * 2018/9/4
     */
    @Transactional(rollbackFor = Exception.class)
    public String getGraphCode(String key) {
        return authRedisDAO.getValueByKey(String.format(GRAPH_CODE_KEY, key));
    }

    /**
     * 删除图片验证码
     *
     * @param key key
     * @author Enma.ai
     * 2018/9/4
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteGraphCode(String key) {
        authRedisDAO.delete(String.format(GRAPH_CODE_KEY, key));
    }
}
