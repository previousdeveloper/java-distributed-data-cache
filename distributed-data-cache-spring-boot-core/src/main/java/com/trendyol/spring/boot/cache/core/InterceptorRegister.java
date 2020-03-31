package com.trendyol.spring.boot.cache.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Lazy
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "distributed.cache.enabled", havingValue = "true")
public class InterceptorRegister implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorRegister.class);
    private final DataCacheInterceptor dataCacheInterceptor;

    @Autowired
    public InterceptorRegister(DataCacheInterceptor dataCacheInterceptor) {
        this.dataCacheInterceptor = dataCacheInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("com.trendyol.spring.boot.cache.core.DataCacheInterceptor registering...");
        registry.addInterceptor(dataCacheInterceptor);
    }
}