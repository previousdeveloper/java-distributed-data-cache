package com.trendyol.quarkus.cache.core;

import provider.CacheOperation;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ResponseFilter implements ContainerResponseFilter {

    @Inject
    private CacheOperation cacheOperation;

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        byte[] denemes = cacheOperation.getCache("deneme");
        containerResponseContext.setEntity(denemes);
    }
}
