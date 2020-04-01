package com.trendyol.quarkus.cache.core;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.trendyol.distributed.data.cache.core.*;
import com.trendyol.distributed.data.cache.core.CachePlatformType;
import com.trendyol.distributed.data.cache.core.CacheProvider;
import com.trendyol.distributed.data.cache.core.CouchbaseCacheProvider;
import com.trendyol.distributed.data.cache.core.DefaultCacheProvider;
import io.quarkus.arc.DefaultBean;

import javax.enterprise.context.Dependent;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class BeanProvider {

//    @Autowired
//    public BaseConfiguration baseConfiguration;

    @Produces
    CouchbaseCacheProvider couchbaseCacheProvider(Bucket bucket) {
        return new CouchbaseCacheProvider(bucket);
    }

    @Produces
    RedisCacheProvider redisCacheProvider() {
        return new RedisCacheProvider();
    }

    @Produces
    DefaultCacheProvider defaultCacheProvider() {
        return new DefaultCacheProvider();
    }

    @Produces
    public CouchbaseCacheProvider provider(Bucket bucket) {
        return new CouchbaseCacheProvider(bucket);
    }

    @Produces
    CacheOperation cacheOperation(CouchbaseCacheProvider provider) {
        return new CacheOperation(provider);
    }

    @DefaultBean
    @Produces
    public Bucket bucket() {
//        CouchbaseCacheConfiguration couchbaseCacheConfiguration = baseConfiguration.getCouchbase();
        Cluster cluster = CouchbaseCluster.create("localhost");
        cluster.authenticate("deneme", "deneme");
        return cluster.openBucket("deneme");
    }


    private Map<String, CacheProvider> allCachePlatforms(Bucket bucket) {
        Map<String, CacheProvider> cachePlatforms = new HashMap<>();
        cachePlatforms.put(CachePlatformType.COUCHBASE.name(), couchbaseCacheProvider(bucket));

//        if (CachePlatformType.COUCHBASE.name().equals(baseConfiguration.getPlatform().name())) {
//            cachePlatforms.put(CachePlatformType.COUCHBASE.name(), couchbaseCacheProvider(bucket));
//        } else {
//            cachePlatforms.put(CachePlatformType.REDIS.name(), redisCacheProvider());
//
//        }

        return cachePlatforms;
    }
}
