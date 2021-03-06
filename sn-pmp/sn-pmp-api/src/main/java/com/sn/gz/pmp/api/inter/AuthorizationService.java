/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: AuthorizationService
 * Author:   lufeiwang
 * Date:     2019/4/16
 */
package com.sn.gz.pmp.api.inter;

import com.sn.gz.core.BusinessException;
import com.sn.gz.pmp.api.dto.auth.AuthenticationResultOutDTO;
import com.sn.gz.pmp.api.dto.auth.MenuOutDTO;
import com.sn.gz.pmp.api.dto.auth.UserLoginInDTO;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权
 *
 * @author lufeiwang
 * 2019/4/16
 */
public interface AuthorizationService {

    boolean isJwtExistCache(Long userId) throws BusinessException;

    void refreshJwt(Long userId) throws BusinessException;

    void saveJWT(String termType, Long id, int time, TimeUnit timeUnit);

    byte[] generateGraphCode(String key) throws IOException, BusinessException;

    AuthenticationResultOutDTO userLogin(UserLoginInDTO userLoginInDTO) throws BusinessException;

}
