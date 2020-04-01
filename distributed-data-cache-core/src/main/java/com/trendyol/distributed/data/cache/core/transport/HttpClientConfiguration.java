package com.trendyol.distributed.data.cache.core.transport;

public class HttpClientConfiguration {
    private String baseUrl;

    public HttpClientConfiguration(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
