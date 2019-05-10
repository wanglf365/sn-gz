/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: AuthorizationService
 * Author:   lufeiwang
 * Date:   2019/4/16
 */
package com.sn.gz.pmp.dsc.service;

import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.core.BusinessException;
import com.sn.gz.core.jwt.JwtUtil;
import com.sn.gz.core.jwt.LoginUser;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.core.utils.Md5Utils;
import com.sn.gz.dscstarter.utils.AnnotationConstants;
import com.sn.gz.pmp.api.dto.auth.AuthenticationResultOutDTO;
import com.sn.gz.pmp.api.dto.auth.UserLoginInDTO;
import com.sn.gz.pmp.api.inter.AuthorizationService;
import com.sn.gz.pmp.dsc.constants.ErrorMessage;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.manager.auth.AuthManager;
import com.sn.gz.pmp.dsc.manager.auth.MenuManager;
import com.sn.gz.pmp.dsc.manager.org.OrgManager;
import com.sn.gz.pmp.dsc.transfer.OrgTransfer;
import com.sn.gz.pmp.dsc.utils.GraphCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 鉴权
 *
 * @author lufeiwang
 * 2019/4/16
 */
@Service(version = AnnotationConstants.VERSION)
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {


    @Resource
    private AuthManager authManager;

    @Resource
    private OrgManager orgManager;

    @Resource
    private MenuManager menuManager;

    public AuthenticationResultOutDTO userLogin(UserLoginInDTO userLoginInDTO) throws BusinessException {
        AuthenticationResultOutDTO authenticationResultOutDTO = new AuthenticationResultOutDTO();
        //1.验证图形验证码
        authManager.graphCodeVerification(userLoginInDTO.getCustomerGraphCode(), userLoginInDTO.getKey());
        UnionUser user = authManager.getUserByPhone(userLoginInDTO.getPhone());
        if (null == user) {
            throw new BusinessException(ErrorMessage.ACCOUNT_NOT_EXIST_IN_SYSTEM);
        }
        //校验账户是否正常
        if (!OrgConstants.UnionUserIsEnable.ENABLE.equals(user.getEnable())) {
            throw new BusinessException(ErrorMessage.USER_NOT_ENABLE);
        }
        //校验密码
        if (!verifyPwd(user.getPassword(), userLoginInDTO.getPassword())) {
            throw new BusinessException(ErrorMessage.ACCOUNT_OR_PASSWORD_ERROR);
        }

        //支持一账户多公司,
        List<Member> memberList = orgManager.listByUnionId(user.getId());
        if (ListUtils.isNull(memberList)) {
            throw new BusinessException(ErrorMessage.ACCOUNT_NOT_BIND_ERROR);
        }

        //现取第一家（往后支持多家，做选择公司的操作）
        Member member = memberList.get(0);

        LoginUser loginUser = OrgTransfer.INSTANCE.mapLoginUser(user);
        loginUser.setTerminalType(userLoginInDTO.getTerminalType());
        loginUser.setGroupId(member.getGroupId());
        loginUser.setMemberId(member.getId());
        loginUser.setLoginTime(new Date());
        String jwt = JwtUtil.createJWT(loginUser);
        authenticationResultOutDTO.setJwt(jwt);

        //获取当前用户的默认加载菜单类型
        String menuType = getDefaultMenuType(member.getId(), member.getGroupId());
        authenticationResultOutDTO.setMenuType(menuType);

        authManager.saveJWT(userLoginInDTO.getTerminalType(), user.getId(), 0, null);
        return authenticationResultOutDTO;
    }

    private String getDefaultMenuType(Long memberId, Long groupId) throws BusinessException {
        return menuManager.getMenuAndPermissionType(memberId, groupId).get(MenuManager.MENU_TYPE_KEY);
    }

    @Override
    public boolean isJwtExistCache(Long userId) throws BusinessException {
        return authManager.isJwtExistCache(userId);
    }

    @Override
    public void refreshJwt(Long userId) throws BusinessException {
        authManager.refreshJwt(userId);
    }

    @Override
    public void saveJWT(String termType, Long id, int time, TimeUnit timeUnit) {
        authManager.saveJWT(termType, id, time, timeUnit);
    }

    @Override
    public byte[] generateGraphCode(String key) throws IOException, BusinessException {
        Object[] objects = GraphCodeUtil.createGraphCode();
        authManager.saveGraphCode(key, (String) objects[1]);
        log.info("图片验证码={}", objects[1]);
        return (byte[]) objects[0];
    }

    private boolean verifyPwd(String sourcePassword, String password) throws BusinessException {
        return Md5Utils.verify(password, sourcePassword);
    }
}
