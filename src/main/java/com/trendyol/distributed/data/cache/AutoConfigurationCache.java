package com.trendyol.distributed.data.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.trendyol.springboot.data.cache", lazyInit = true)
@ConditionalOnProperty(name = "distributed.cache.enabled", havingValue = "true")
public class AutoConfigurationCache {
}
