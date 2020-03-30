package com.trendyol.distributed.data.cache;

import com.couchbase.client.java.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class ShutdownHookListener {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownHookListener.class);
    @Autowired
    private Bucket bucket;

    @PreDestroy
    public void shutDown() {
        bucket.close();
        logger.info("Resource released");
    }
}
