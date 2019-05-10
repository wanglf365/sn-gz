/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: AuthRedisDao
 * Author:   lufeiwang
 * Date:   2019/4/16
 */
package com.sn.gz.pmp.dsc.dao.auth;

import com.sn.gz.redis.starter.AbstractRedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis
 *
 * @author lufeiwang
 * 2019/4/16
 */
@Component
public class AuthRedisDAO extends AbstractRedisDao {

    /**
     * 成员权限key
     */
    private static final String ACCOUNT_AUTH_GROUP_MEMBER_PERMS = "ACCOUNT.AUTH.MEMBER.PERMS.%d.%d";
    /**
     * 用于标记用户权限是否已经重新加载过key
     */
    public static final String ACCOUNT_AUTH_MEMBER_PERMS_RELOAD = "ACCOUNT_AUTH_MEMBER_PERMS_RELOAD";
    /**
     * 权限key对应value
     */
    private static final String ACCOUNT_AUTH_USER_AUTH_VALUE = "AUTH";
    /**
     * jwt存储key
     */
    private static final String ACCOUNT_AUTH_USER_UNION_JWT = "ACCOUNT.AUTH.USER.UNION.JWT.%s";

    /**
     * jwt value(不保存具体的jwt的值，因为jwt只判断是否失效，没有其他作用，也是为了节省空间)
     */
    private static final String ACCOUNT_AUTH_USER_UNION_JWT_VALUE = "JWT";

    /**
     * jwt失效时间
     */
    private static final Integer JWT_EXPIRE_TIME = 600;

    public AuthRedisDAO(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }

    /**
     * 从缓存中获取用户jwt标识
     *
     * @param unionId unionid
     * @return boolean 存在返回true
     * @author xiaoxueliang
     * 2018/7/2
     */
    public boolean isJwtExistCache(Long unionId) {
        String key = String.format(ACCOUNT_AUTH_USER_UNION_JWT, unionId);
        String value = getValueByKey(key);
        return StringUtils.isNotBlank(value);
    }

    /**
     * 刷新用户权限在redis中的过期时间（1小时过期）
     *
     * @param unionId 用户id
     * @author lufeiwang
     * 2019/4/16
     */
    public void refreshJwt(Long unionId) {
        String userPermKey = String.format(ACCOUNT_AUTH_USER_UNION_JWT, unionId);
        expire(userPermKey, JWT_EXPIRE_TIME, TimeUnit.MINUTES);
    }

    /**
     * 保存定时器
     *
     * @param terminalType 终端类型
     * @param id           id
     * @author Enma.ai
     * 2018/3/27
     */
    public void saveJWT(String terminalType, Long id, int time, TimeUnit timeUnit) {
        if (null == timeUnit) {
            saveKeyValue(String.format(ACCOUNT_AUTH_USER_UNION_JWT, id), AuthRedisDAO.ACCOUNT_AUTH_USER_UNION_JWT_VALUE, JWT_EXPIRE_TIME, TimeUnit.MINUTES);
        } else {
            saveKeyValue(String.format(ACCOUNT_AUTH_USER_UNION_JWT, id), AuthRedisDAO.ACCOUNT_AUTH_USER_UNION_JWT_VALUE, time, timeUnit);
        }
    }

    public List<String> getMemberPermissions(Long groupId, Long memberId, List<String> permissions) {
        String hashKey = String.format(ACCOUNT_AUTH_GROUP_MEMBER_PERMS, groupId, memberId);
        return hashMultiGet(hashKey, permissions);
    }


    /**
     * 重新加载功能权限
     *
     * @param userOperationPermissionList 权限
     * @param groupId                     集团id
     * @param memberId                    成员id
     * @author lufeiwang
     * 2019/5/6
     */
    public void reloadOperationPermission(List<String> userOperationPermissionList, Long groupId, Long memberId) {
        HashMap<String, String> authMap = new HashMap<>(16);
        for (String userPermission : userOperationPermissionList) {
            authMap.put(userPermission, ACCOUNT_AUTH_USER_AUTH_VALUE);
        }
        //标记为重新加载过
        authMap.put(ACCOUNT_AUTH_MEMBER_PERMS_RELOAD, Boolean.TRUE.toString());
        String key = String.format(ACCOUNT_AUTH_GROUP_MEMBER_PERMS, groupId, memberId);
        delete(key);
        saveHash(key, authMap);
    }

}
