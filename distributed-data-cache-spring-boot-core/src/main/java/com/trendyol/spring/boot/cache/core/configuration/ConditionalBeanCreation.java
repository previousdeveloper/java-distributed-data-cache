package com.trendyol.spring.boot.cache.core.configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.trendyol.distributed.data.cache.core.*;
import com.trendyol.distributed.data.cache.core.transport.HttpClientWrapper;
import com.trendyol.spring.boot.cache.core.DataCacheInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.trendyol.distributed.data.cache.core.utils.HashUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConditionalBeanCreation {

    @Autowired
    public BaseConfiguration baseConfiguration;

    @Bean
    CouchbaseCacheProvider couchbaseCacheProvider() {
        return new CouchbaseCacheProvider(getBucket());
    }

    @Bean
    CacheOperation cacheOperation(CacheProvider provider) {
        return new CacheOperation(provider);
    }

    @Bean
    DataCacheInterceptor dataCacheInterceptor(CacheOperation cacheOperation) {
        return new DataCacheInterceptor(cacheOperation, new HashUtils());
    }

    @Bean
    HttpCacheProvider httpCacheProvider(HttpClientWrapper httpClientWrapper) {
        return new HttpCacheProvider(httpClientWrapper);
    }

    @Bean
    RedisCacheProvider redisCacheProvider() {
        return new RedisCacheProvider();
    }

    @Bean
    DefaultCacheProvider defaultCacheProvider() {
        return new DefaultCacheProvider();
    }

    @Bean
    public CacheProvider provider(HttpClientWrapper httpClientWrapper) {
        return allCachePlatforms(httpClientWrapper).getOrDefault(baseConfiguration.getPlatform().name(), new DefaultCacheProvider());
    }

    public Bucket getBucket() {
        CouchbaseCacheConfiguration couchbaseCacheConfiguration = baseConfiguration.getCouchbase();
        Cluster cluster = CouchbaseCluster.create(couchbaseCacheConfiguration.getBootstrapHosts());
        cluster.authenticate(couchbaseCacheConfiguration.getUsername(), couchbaseCacheConfiguration.getPassword());
        return cluster.openBucket(couchbaseCacheConfiguration.getBucket());
    }


    private Map<String, CacheProvider> allCachePlatforms(HttpClientWrapper httpClientWrapper) {
        Map<String, CacheProvider> cachePlatforms = new HashMap<>();
        if (CachePlatformType.COUCHBASE.name().equals(baseConfiguration.getPlatform().name())) {
            cachePlatforms.put(CachePlatformType.COUCHBASE.name(), couchbaseCacheProvider());
        } else if (CachePlatformType.REDIS.name().equals(baseConfiguration.getPlatform().name())) {
            cachePlatforms.put(CachePlatformType.REDIS.name(), redisCacheProvider());
        } else if (CachePlatformType.HTTP.name().equals(baseConfiguration.getPlatform().name())) {
            cachePlatforms.put(CachePlatformType.HTTP.name(), httpCacheProvider(httpClientWrapper));
        }

        return cachePlatforms;
    }
}
