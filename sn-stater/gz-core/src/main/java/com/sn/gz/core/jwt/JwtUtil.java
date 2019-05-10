/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: JWTUtil
 * Author:   Enma.ai
 * Date:     2018/3/21
 */
package com.sn.gz.core.jwt;

import com.alibaba.fastjson.JSON;
import com.sn.gz.core.sandbox.GroupConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * JWT工具类，加密解密JWT
 *
 * @author Enma.ai
 * 2018/3/21
 */
@Slf4j
public class JwtUtil {

    private static final String JWT_SECRET = "SHINIAN_Technology";
    private static final Long TTLMILLIS = 3600000L;

    private JwtUtil() {
    }

    /**
     * JWT_SECRET加密
     *
     * @return javax.crypto.SecretKey
     * @author Enma.ai
     * 2018/3/22
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.decodeBase64(JWT_SECRET);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 创建JWT
     *
     * @param loginUser LoginUser
     * @return java.lang.String
     * @author Enma.ai
     * 2018/3/26
     */
    public static String createJWT(LoginUser loginUser) {
        //签名算法&秘钥
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey key = generalKey();

        JwtBuilder builder = Jwts.builder()
                //设置用户信息
                .setSubject(JSON.toJSONString(loginUser))
                //设置签名算法和秘钥
                .signWith(signatureAlgorithm, key);

        //构建jwt
        return builder.compact();
    }

    /**
     * 创建临时JWT，实效1H，用于账号注册、密码重置等操作
     *
     * @param loginUser LoginUser
     * @return java.lang.String
     * @author Enma.ai
     * 2018/3/26
     */
    public static String createTemporaryJWT(LoginUser loginUser) {
        //签名算法&秘钥
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey key = generalKey();
        /*设置失效时间*/
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + TTLMILLIS;
        Date exp = new Date(expMillis);

        JwtBuilder builder = Jwts.builder()
                //设置用户信息
                .setSubject(JSON.toJSONString(loginUser))
                //设置签名算法和秘钥
                .signWith(signatureAlgorithm, key)
                .setExpiration(exp);
        //构建jwt
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT字符串
     * @return com.dianji.pangu.auth.dsc.util.LoginUser
     * @author Enma.ai
     * 2018/3/26
     */
    public static LoginUser parseJWT(String jwt) {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return JSON.parseObject(claims.getSubject(), LoginUser.class);
    }


    public static void main(String[] arg) {
        LoginUser loginUser = new LoginUser();
        loginUser.setMemberId(100L);
        loginUser.setGroupId(1001L);
        loginUser.setPhone("18718519232");
        loginUser.setTerminalType(GroupConstants.TERMINAL_TYPE_PC);
        loginUser.setLoginTime(new Date());
        loginUser.setUnionId(200L);

        System.out.println(createJWT(loginUser));

    }
}

