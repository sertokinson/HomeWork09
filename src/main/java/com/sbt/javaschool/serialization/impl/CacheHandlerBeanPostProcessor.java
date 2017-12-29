package com.sbt.javaschool.serialization.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class CacheHandlerBeanPostProcessor implements BeanPostProcessor {
    public static List<Value> list = new ArrayList<Value>();
    public static final String FILE_NAME = "cash.ser";

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;

    }

    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectOutputStream finalOos = oos;
        return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (Object proxy, Method method, Object[] args) -> {
            Method methodImpl = beanClass.getMethod(method.getName(), method.getParameterTypes());
            Cache annotation = methodImpl.getAnnotation(Cache.class);
            if (annotation != null) {
                if (annotation.cashType() == CacheType.MEMORY)
                    return CacheAnnotation.cashingInMemory(bean, method, args, list, annotation);
                if (annotation.cashType() == CacheType.FILE) {
                    return CacheAnnotation.cashingInFile(bean, method, args, annotation, finalOos);
                }
            }
            return method.invoke(bean, args);
        });
    }

}

