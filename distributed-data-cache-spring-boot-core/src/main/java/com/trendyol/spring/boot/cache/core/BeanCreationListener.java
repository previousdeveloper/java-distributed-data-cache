package com.trendyol.spring.boot.cache.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import provider.MethodAnnotationMapping;
import provider.ResponseCache;

import java.lang.reflect.Method;
import java.util.Objects;

@Component
public class BeanCreationListener implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        for (Method declaredMethod : aClass.getDeclaredMethods()) {
            ResponseCache annotation = declaredMethod.getAnnotation(ResponseCache.class);
            if (Objects.nonNull(annotation)) {
                MethodAnnotationMapping.put(declaredMethod.getName(), annotation);
            }
        }

        return bean;
    }
}

