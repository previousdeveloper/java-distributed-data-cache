package com.trendyol.spring.boot.cache.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import provider.CacheOperation;
import provider.MethodAnnotationMapping;
import provider.ResponseCache;

import java.time.Duration;

@RestControllerAdvice
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.trendyol.springboot.data.cache", lazyInit = true)
@ConditionalOnProperty(name = "distributed.cache.enabled")
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(CustomResponseBodyAdviceAdapter.class);

    private CacheOperation cacheOperation;
    private ObjectMapper objectMapper;

    @Autowired
    public CustomResponseBodyAdviceAdapter(CacheOperation cacheOperation, ObjectMapper objectMapper) {
        logger.info("com.trendyol.spring.boot.cache.core.CustomResponseBodyAdviceAdapter registering...");
        this.cacheOperation = cacheOperation;
        this.objectMapper = objectMapper;
    }


    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            try {
                  ResponseCache responseCache = MethodAnnotationMapping.getMethodAnnotationPair().get(methodParameter.getMethod().getName());
                Object key = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getAttribute("request-hash");
                int responseStatus = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse().getStatus();
                if (responseStatus == 200) {
                    cacheOperation.createCache(key.toString(), objectMapper.writeValueAsBytes(o), Duration.ofMinutes(responseCache.expireInMinutes()));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return o;
    }
}