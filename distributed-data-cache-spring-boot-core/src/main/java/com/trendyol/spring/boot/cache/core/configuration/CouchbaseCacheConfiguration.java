package com.trendyol.spring.boot.cache.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "couchbase")
public class CouchbaseCacheConfiguration {

    private String bootstrapHosts;
    private String bucket;
    private String username;
    private String password;


    public String getBootstrapHosts() {
        return bootstrapHosts;
    }

    public void setBootstrapHosts(String bootstrapHosts) {
        this.bootstrapHosts = bootstrapHosts;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
