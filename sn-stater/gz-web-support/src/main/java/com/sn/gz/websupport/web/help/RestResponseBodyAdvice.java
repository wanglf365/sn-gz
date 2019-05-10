/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: RestResponseBodyAdvice
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.websupport.web.help;


import com.sn.gz.core.Representation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * 为了统一前端返回格式
 * @since 1.0.0
 * @author luffy
 * Date: 2018/3/21 21:12
 */
@ControllerAdvice
@ConditionalOnProperty(value = "pangu.rest.response.enabled", matchIfMissing = true)
@Slf4j
public class RestResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @InitBinder
    public void binder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target != null && (Iterable.class.isAssignableFrom(target.getClass()) || target.getClass().isArray())) {
            // 扩展对列表的支持
            log.error("列表参数，替换validator为IterableValidator");
            List<Validator> validators = binder.getValidators();
            binder.setValidator(new IterableValidator(validators));
        }
    }

    @Override
    public boolean supports(MethodParameter returnType,
        Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> clazz = returnType.getMethod().getReturnType();
        return !clazz.isAssignableFrom(Representation.class)
            && !clazz.isAssignableFrom(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
        MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType,
        ServerHttpRequest request, ServerHttpResponse response) {
        return new Representation<>(body);
    }
}
