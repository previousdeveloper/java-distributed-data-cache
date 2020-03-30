package com.trendyol.distributed.data.cache;

import com.trendyol.distributed.data.cache.provider.CacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.time.Duration;

public class CacheOperation {
    private final CacheProvider provider;

    @Autowired
    public CacheOperation(CacheProvider provider) {
        this.provider = provider;
    }

    public byte[] getCache(String key) {
        return provider.get(key);
    }

    @Async(value = "cacheOperationExecutor")
    public void createCache(String key, byte[] obj, Duration ttl) {
        provider.add(key, obj, ttl);
    }

}
