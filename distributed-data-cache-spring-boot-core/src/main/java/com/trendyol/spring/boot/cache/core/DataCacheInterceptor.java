package com.trendyol.spring.boot.cache.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import provider.CacheOperation;
import provider.MethodAnnotationMapping;
import provider.ResponseCache;
import utils.HashUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@Lazy
@ConditionalOnProperty(name = "distributed.cache.enabled", havingValue = "true")
@ComponentScan(basePackages = "com.trendyol.springboot.data.cache", lazyInit = true)
public class DataCacheInterceptor extends HandlerInterceptorAdapter {

    private final CacheOperation cacheOperation;
    private final HashUtils hashUtils;

    @Autowired
    public DataCacheInterceptor(CacheOperation cacheOperation, HashUtils hashUtils) {
        this.cacheOperation = cacheOperation;
        this.hashUtils = hashUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ResponseCache responseCache = MethodAnnotationMapping.getMethodAnnotationPair().get(handlerMethod.getMethod().getName());

        if (responseCache.enabled() && "GET".equalsIgnoreCase(requestServlet.getMethod())) {
            String key = hashUtils.hash(requestServlet.getQueryString());
            byte[] s = cacheOperation.getCache(key);

            if (Objects.nonNull(s)) {
                responseServlet.setContentType("application/json");
                responseServlet.setStatus(SC_OK);
                responseServlet.getOutputStream().write(s);
                return false;
            } else {
                requestServlet.setAttribute("request-hash", key);
                return true;
            }
        }

        return true;
    }


}


