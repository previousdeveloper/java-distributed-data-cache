package com.trendyol.spring.boot.cache.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import provider.CachePlatformType;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "distributed.cache")
public class BaseConfiguration {

    private String enabled;
    private CachePlatformType platform;
    private CouchbaseCacheConfiguration couchbase;

    public CachePlatformType getPlatform() {
        return platform;
    }

    public void setPlatform(CachePlatformType platform) {
        this.platform = platform;
    }

    public CouchbaseCacheConfiguration getCouchbase() {
        return couchbase;
    }

    public void setCouchbase(CouchbaseCacheConfiguration couchbase) {
        this.couchbase = couchbase;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
