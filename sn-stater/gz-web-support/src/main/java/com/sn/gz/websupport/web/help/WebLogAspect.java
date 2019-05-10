/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: WebLogAspect
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.websupport.web.help;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * web切面拦截
 *
 * @author luffy
 * Date: 2018/3/21 21:08
 * @since 1.0.0
 */
@Aspect
@Component
@Slf4j
@ConditionalOnClass(JoinPoint.class)
public class WebLogAspect {
    @Pointcut("execution(public * com.sn..*.controller..*.*(..))"
            + " && (@annotation(org.springframework.web.bind.annotation.RequestMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.GetMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.PutMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.PostMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.PatchMapping)"
            + "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)"
            + ")")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        HttpServletRequest request = getRequest();

        // 记录下请求内容
        log.info("url={}, httpMethod={}, method={}, ip={}, args={}",
                request.getRequestURI(),
                request.getMethod(),
                joinPoint.getSignature().getName(),
                request.getRemoteAddr(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "webLog()", returning = "retValue")
    public void doAfterReturning(JoinPoint joinPoint, Object retValue) {
        String uri = getRequest().getRequestURI();
        // 处理完请求，返回内容
        log.debug("uri={}, return={}", uri, retValue);
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
