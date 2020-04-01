package com.trendyol.distributed.data.cache.core;


import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.ByteArrayDocument;

import java.time.Duration;
import java.util.Objects;

public class CouchbaseCacheProvider implements CacheProvider {
    private final Bucket bucket;

    public CouchbaseCacheProvider(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public byte[] get(String key) {
        ByteArrayDocument byteArrayDocument = bucket.get(key, ByteArrayDocument.class);
        if (Objects.nonNull(byteArrayDocument) && Objects.nonNull(byteArrayDocument.content())) {
            return byteArrayDocument.content();
        }
        return null;
    }

    @Override
    public void add(String key, byte[] payload, Duration ttl) {
        bucket.upsert(ByteArrayDocument.create(key, (int) ttl.getSeconds(), payload));
    }

}


