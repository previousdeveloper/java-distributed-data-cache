package com.trendyol.distributed.data.cache.core;


import java.time.Duration;

public class CacheOperation {
    private final CacheProvider provider;

    public CacheOperation(CacheProvider provider) {
        this.provider = provider;
    }

    public byte[] getCache(String key) {
        return provider.get(key);
    }

    public void createCache(String key, byte[] obj, Duration ttl) {
        provider.add(key, obj, ttl);
    }

}
