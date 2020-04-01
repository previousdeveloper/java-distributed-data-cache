package com.trendyol.distributed.data.cache.core;

import java.time.Duration;

public interface CacheProvider {
    byte[] get(String key);

    void add(String key, byte[] payload, Duration ttl);
}
