package com.trendyol.spring.boot.cache.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.trendyol.spring.boot.cache.core", lazyInit = true)
@ConditionalOnProperty(name = "distributed.cache.enabled", havingValue = "true")
public class AutoConfigurationCache {
}
