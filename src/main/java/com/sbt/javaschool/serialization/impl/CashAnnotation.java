package com.sbt.javaschool.serialization.impl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CashAnnotation {
    public static Object cashingInMemory(Object bean, Method method, Object[] args, List<Value> list, Cash annotation) throws InvocationTargetException, IllegalAccessException {
        for (Value value:list) {
            switch (annotation.countArguments()){
                case 2:
                    if(value.equals(new Value((String)args[0],(Integer)args[1]))){
                        System.out.println("Взяли из кеша:");
                        return value.result;
                    }
                case 1:
                    if(value.name.equals(args[0])){
                        System.out.println("Взяли из кеша:");
                        return value.result;
                    }
            }
        }
        System.out.println("Записали в кеш: ");
        Object result=method.invoke(bean,args);
        list.add(new Value((String)args[0],(Integer)args[1],(Double)result));
        return result;
    }
    public static Object cashingInFile(Object bean, Method method, Object[] args, Cash annotation, ObjectOutputStream oos) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        FileInputStream fis = new FileInputStream("cash.ser");
        ObjectInputStream ois=new ObjectInputStream(fis);
        while(fis.available()>0){
            Value value=(Value)ois.readObject();
            switch (annotation.countArguments()){
                case 2:
                    if(value.equals(new Value((String)args[0],(Integer)args[1]))){
                        System.out.println("Взяли из кеша:");
                        ois.close();
                        return value.result;
                    }
                case 1:
                    if(value.name.equals(args[0])){
                        System.out.println("Взяли из кеша:");
                        ois.close();
                        return value.result;
                    }
            }
        }
        System.out.println("Записали в кеш:");
        Object result=method.invoke(bean,args);
        oos.writeObject(new Value((String)args[0],(Integer)args[1],(Double)result));
        return result;
    }
}
