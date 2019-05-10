/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: SwaggerConfigurer
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.websupport.web.swagger;

import com.google.common.base.Strings;
import com.sn.gz.core.sandbox.GroupConstants;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * swagger配置
 *
 * @author luffy
 * Date: 2018/3/21 21:05
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigurer {

    @Value("${spring.application.name}")
    private String application;

    @Value("${swagger.api.version:1.0}")
    private String version;

    @Value("${swagger.api.title:}")
    private String title;

    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = input -> {
            Class<?> declaringClass = input.declaringClass();
            if (declaringClass == BasicErrorController.class) {
                return false;
            }
            //只有带@ApiOperation注解的方生成接口文档
            return input.isAnnotatedWith(ApiOperation.class) && declaringClass
                    .isAnnotationPresent(RestController.class);
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(globalOperationParameters())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate::test)
                .build();
    }

    private List<Parameter> globalOperationParameters() {
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name(GroupConstants.TOKEN).description("jwt令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(tokenPar.build());
        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(Strings.isNullOrEmpty(title) ? application : title)
                .version(version)
                .build();
    }

}
