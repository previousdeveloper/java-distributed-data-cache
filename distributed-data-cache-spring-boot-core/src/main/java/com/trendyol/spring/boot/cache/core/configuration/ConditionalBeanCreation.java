package com.trendyol.spring.boot.cache.core.configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.trendyol.spring.boot.cache.core.DataCacheInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import provider.*;
import utils.HashUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConditionalBeanCreation {

    @Autowired
    public BaseConfiguration baseConfiguration;

    @Bean
    CouchbaseCacheProvider couchbaseCacheProvider(Bucket bucket) {
        return new CouchbaseCacheProvider(bucket);
    }

    @Bean
    DataCacheInterceptor dataCacheInterceptor(CacheOperation cacheOperation) {
        return new DataCacheInterceptor(cacheOperation, new HashUtils());
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
    public CacheProvider provider(Bucket bucket) {
        return allCachePlatforms(bucket).getOrDefault(baseConfiguration.getPlatform().name(), new DefaultCacheProvider());
    }

    @Bean
    CacheOperation cacheOperation(CacheProvider provider) {
        return new CacheOperation(provider);
    }

    @ConditionalOnProperty(prefix = "distributed.cache", name = "platform", matchIfMissing = true, havingValue = "COUCHBASE")
    @Bean
    public Bucket bucket() {
        CouchbaseCacheConfiguration couchbaseCacheConfiguration = baseConfiguration.getCouchbase();
        Cluster cluster = CouchbaseCluster.create(couchbaseCacheConfiguration.getBootstrapHosts());
        cluster.authenticate(couchbaseCacheConfiguration.getUsername(), couchbaseCacheConfiguration.getPassword());
        return cluster.openBucket(couchbaseCacheConfiguration.getBucket());
    }


    private Map<String, CacheProvider> allCachePlatforms(Bucket bucket) {
        Map<String, CacheProvider> cachePlatforms = new HashMap<>();
        if (CachePlatformType.COUCHBASE.name().equals(baseConfiguration.getPlatform().name())) {
            cachePlatforms.put(CachePlatformType.COUCHBASE.name(), couchbaseCacheProvider(bucket));
        } else {
            cachePlatforms.put(CachePlatformType.REDIS.name(), redisCacheProvider());

        }

        return cachePlatforms;
    }
}
