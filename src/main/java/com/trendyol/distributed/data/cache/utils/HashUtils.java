package com.trendyol.distributed.data.cache.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class HashUtils {

    public String hash(String text) {
        //TODO: Performance ?
        return new String(DigestUtils.md5Digest(text.getBytes()));
    }
}
