/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: UserContext
 * Author:   luffy
 * Date:   2018/4/26 19:09
 * Since: 1.0.0
 */
package com.sn.gz.core.sandbox;

import com.sn.gz.core.BusinessException;
import com.sn.gz.core.jwt.LoginUser;
import org.apache.dubbo.common.threadlocal.InternalThreadLocal;

/**
 * 登录用户上下文
 *
 * @author luffy
 * @since 1.0.0
 * 2018/4/26
 */
public class UserContext {

    /**
     * 2018/4/26 19:13
     *
     * @since 1.0.0
     */
    private static final InternalThreadLocal<LoginUser> contextHolder = new InternalThreadLocal<LoginUser>() {
        @Override
        protected LoginUser initialValue() {
            return new LoginUser();
        }
    };

    /**
     * @return LoginUser
     * @author luffy
     * 2018/4/26 19:14
     * @since 1.0.0
     */
    public static LoginUser getLoginUser() {
        return contextHolder.get();
    }

    /**
     * @return java.lang.Long
     * @author lufeiwang
     * 2019/4/18
     */
    public static Long getContextGroupId() {
        LoginUser loginUser = getLoginUser();
        if (null == loginUser || null == loginUser.getGroupId() || null == loginUser.getUnionId()) {
            throw new BusinessException(GroupConstants.INVALID_CONTEXT);
        }
        return loginUser.getGroupId();
    }

    /**
     * @return java.lang.Long
     * @author lufeiwang
     * 2019/4/18
     */
    public static Long getContextMemberId() {
        LoginUser loginUser = getLoginUser();
        if (null == loginUser || null == loginUser.getGroupId() || null == loginUser.getUnionId()) {
            throw new BusinessException(GroupConstants.INVALID_CONTEXT);
        }
        return loginUser.getMemberId();
    }

    /**
     * 获取
     *
     * @param loginUser 用户登录信息
     * @author luffy
     * 2018/4/26 19:19
     * @since 1.0.0
     */
    public static void setLoginUser(LoginUser loginUser) {
        contextHolder.set(loginUser);
    }
}
