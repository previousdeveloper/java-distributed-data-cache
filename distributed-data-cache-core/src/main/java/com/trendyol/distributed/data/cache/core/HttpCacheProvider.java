package com.trendyol.distributed.data.cache.core;


import com.trendyol.distributed.data.cache.core.transport.HttpClientWrapper;

import java.time.Duration;

public class HttpCacheProvider implements CacheProvider {
    private final HttpClientWrapper httpClientWrapper;

    public HttpCacheProvider(HttpClientWrapper httpClientWrapper) {
        this.httpClientWrapper = httpClientWrapper;
    }

    @Override
    public byte[] get(String key) {
        return httpClientWrapper.get(key);
    }

    @Override
    public void add(String key, byte[] payload, Duration ttl) {
        httpClientWrapper.post(key, payload);
    }
}
