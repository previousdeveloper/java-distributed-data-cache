package com.trendyol.spring.boot.cache.core.configuration;

import com.trendyol.distributed.data.cache.core.transport.HttpClientWrapper;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpBeanCreation {

    @Bean
    public HttpClientWrapper httpClientWrapper() {
        return new HttpClientWrapper(HttpClients.createMinimal());
    }

}
