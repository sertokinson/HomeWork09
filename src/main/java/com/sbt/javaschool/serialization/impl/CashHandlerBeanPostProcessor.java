package com.sbt.javaschool.serialization.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashHandlerBeanPostProcessor implements BeanPostProcessor {
    List<Value> list=new ArrayList<Value>();
    public static final String FILE_NAME="C:\\Users\\Сергей\\Desktop\\JavaSchool\\Homeworks\\HomeWork_09\\new\\serialization\\cash.ser";
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            return bean;

        }

        public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
            Class<?> beanClass = bean.getClass();
            Method[]methods=beanClass.getMethods();
            for (Method currentMethod:methods) {
                Cash annotation=currentMethod.getAnnotation(Cash.class);
                if (annotation != null) {
                    if(annotation.cashType()==CashType.MEMORY)
                        return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> CashAnnotation.cashingInMemory(bean, method, args, list,annotation));
               if(annotation.cashType()==CashType.FILE){
                   try {
                       ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(CashHandlerBeanPostProcessor.FILE_NAME));
                       return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> CashAnnotation.cashingInFile(bean, method, args,annotation,oos));

                   } catch (IOException e) {
                       e.printStackTrace();
                   }

               }
                }
            }
            return bean;
        }
    }

