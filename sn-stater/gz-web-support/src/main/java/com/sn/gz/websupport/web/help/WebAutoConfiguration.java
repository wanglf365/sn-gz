/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: WebAutoConfiguration
 * Author:   lufeiwang
 * Date:     2018/3/31 17:04
 *
 * @since 1.0.0
 */
package com.sn.gz.websupport.web.help;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryCustomizer;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

/**
 * web 配置
 * @author lufeiwang
 * Date 2018/3/31
 * @since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@AutoConfigureBefore({WebMvcAutoConfiguration.class, ErrorMvcAutoConfiguration.class})
public class WebAutoConfiguration implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    private static final String NOT_FOUND_PAGE = "/error.json";
    @Bean
    @ConditionalOnMissingBean()
    @ConditionalOnProperty(prefix = "spring.web.error.attribute", name = "enabled", matchIfMissing = true)
    public TopErrorAttributes errorAttributes(MessageSource messageSource) {
        return new TopErrorAttributes(messageSource);
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                protocol.setMaxConnections(200);
                protocol.setMaxThreads(200);
                protocol.setSelectorTimeout(3000);
                protocol.setSessionTimeout(3000);
                protocol.setConnectionTimeout(3000);
            }
        });
    }

}
