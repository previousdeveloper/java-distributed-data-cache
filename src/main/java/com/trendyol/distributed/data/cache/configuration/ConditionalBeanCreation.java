package com.trendyol.distributed.data.cache.configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.trendyol.distributed.data.cache.DataCacheInterceptor;
import com.trendyol.distributed.data.cache.CacheOperation;
import com.trendyol.distributed.data.cache.CachePlatformType;
import com.trendyol.distributed.data.cache.provider.CacheProvider;
import com.trendyol.distributed.data.cache.provider.CouchbaseCacheProvider;
import com.trendyol.distributed.data.cache.provider.DefaultCacheProvider;
import com.trendyol.distributed.data.cache.provider.RedisCacheProvider;
import com.trendyol.distributed.data.cache.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class ConditionalBeanCreation {

    @Autowired
    public BaseConfiguration baseConfiguration;

    @ConditionalOnProperty(prefix = "distributed.cache", name = "platform", matchIfMissing = true, havingValue = "COUCHBASE")
    @Bean
    CouchbaseCacheProvider couchbaseCacheProvider() {
        return new CouchbaseCacheProvider(bucket());
    }

    @Bean
    DataCacheInterceptor dataCacheInterceptor() {
        return new DataCacheInterceptor(cacheOperation(), new HashUtils());
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
    CacheOperation cacheOperation() {
        return new CacheOperation(provider());
    }

    @ConditionalOnProperty(prefix = "distributed.cache", name = "platform", matchIfMissing = true, havingValue = "COUCHBASE")
    @Bean
    public Bucket bucket() {
        CouchbaseCacheConfiguration couchbaseCacheConfiguration = baseConfiguration.getCouchbase();
        Cluster cluster = CouchbaseCluster.create(couchbaseCacheConfiguration.getBootstrapHosts());
        cluster.authenticate(couchbaseCacheConfiguration.getUsername(), couchbaseCacheConfiguration.getPassword());
        return cluster.openBucket(couchbaseCacheConfiguration.getBucket());
    }

    @Bean
    public CacheProvider provider() {
        return allCachePlatforms().getOrDefault(baseConfiguration.getPlatform(), new DefaultCacheProvider());
    }

    public Map<String, CacheProvider> allCachePlatforms() {
        Map<String, CacheProvider> cachePlatforms = new HashMap<>();
        if (CachePlatformType.COUCHBASE.name().equals(baseConfiguration.getPlatform())) {
            cachePlatforms.put(CachePlatformType.COUCHBASE.name(), couchbaseCacheProvider());
        } else {
            cachePlatforms.put(CachePlatformType.REDIS.name(), redisCacheProvider());

        }

        return cachePlatforms;
    }
}
