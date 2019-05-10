/**
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: ContextConsumerFilter
 * Author:   lufeiwang
 * Date:     2018/4/12 21:26
 *
 * @since 1.0.0
 */
package com.sn.gz.pmp.dsc.filter;


import com.alibaba.fastjson.JSON;
import com.sn.gz.core.jwt.LoginUser;
import com.sn.gz.core.sandbox.GroupConstants;
import com.sn.gz.core.sandbox.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * dubbo 提供者filter
 *
 * @author lufeiwang
 * 2019/4/16
 */
@Slf4j
@Activate(group = Constants.PROVIDER, value = "contextProviderFilter",order = -110000)
public class ContextProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String loginUserJson = RpcContext.getContext().getAttachment(GroupConstants.RPC_LOGIN_USER_KEY);

        LoginUser loginUser = JSON.parseObject(loginUserJson, LoginUser.class);
        UserContext.setLoginUser(loginUser);

        try {
            return invoker.invoke(invocation);
        } finally {
            // 调用完成后清理attachments中的数据
            RpcContext.getContext().clearAttachments();
        }
    }
}
