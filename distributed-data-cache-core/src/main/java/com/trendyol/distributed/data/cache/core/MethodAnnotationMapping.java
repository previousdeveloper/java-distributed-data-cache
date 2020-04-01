package com.trendyol.distributed.data.cache.core;

import java.util.HashMap;
import java.util.Map;

public class MethodAnnotationMapping {
    private final static Map<String, ResponseCache> methodAnnotationPair = new HashMap<>();

    public static void put(String name, ResponseCache responseCache) {
        methodAnnotationPair.put(name, responseCache);
    }

    public static Map<String, ResponseCache> getMethodAnnotationPair() {
        return methodAnnotationPair;
    }
}
