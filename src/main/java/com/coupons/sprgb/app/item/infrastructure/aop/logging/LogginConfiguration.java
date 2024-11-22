package com.coupons.sprgb.app.item.infrastructure.aop.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class LogginConfiguration {
    @Bean
    @Scope(value= WebApplicationContext.SCOPE_REQUEST,proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AnaliticsDdr.Builder getRequestScopeDdr(){
        return AnaliticsDdr.builder();
    }
}
