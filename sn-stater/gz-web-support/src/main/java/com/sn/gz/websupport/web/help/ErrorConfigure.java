/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: ErrorConfigurar
 * Author:   lufeiwang
 * Date:   2019/4/9
 */
package com.sn.gz.websupport.web.help;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 错误配置
 *
 * @author lufeiwang
 * 2019/4/9
 */
@Component
public class ErrorConfigure implements ErrorPageRegistrar {

    private static final String NOT_FOUND_PAGE = "/error.json";
    private static final String INTERNAL_ERROR = "/internal_error.json";

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[2];
        errorPages[0] = new ErrorPage(HttpStatus.NOT_FOUND, NOT_FOUND_PAGE);
        errorPages[1] = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR);

        registry.addErrorPages(errorPages);
    }
}
