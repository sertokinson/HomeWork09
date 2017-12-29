package com.sbt.javaschool.serialization.impl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CacheAnnotation {
    private static int argument;
    private static boolean isList;

    public static Object cashingInMemory(Object bean, Method method, Object[] args, List<Value> list, Cache annotation) throws InvocationTargetException, IllegalAccessException {
        argument = annotation.countArguments();
        isList = method.getReturnType() == List.class;
        if (isList) {
            argument = 1;
        }
        for (Value value : list) {
            switch (argument) {
                case 2:
                    if (value.equals(new Value((String) args[0], (Integer) args[1]))) {
                        System.out.println("Взяли из памяти:");
                        return value.result;
                    }
                    break;
                case 1:
                    if (value.name.equals(args[0])) {
                        System.out.println("Взяли из памяти:");
                        if (isList)
                            return list;
                        return value.result;
                    }
            }
        }
        Object result = method.invoke(bean, args);
        if (isList) {
            System.out.println("Записали в память list: ");
            list.add(new Value((String) args[0]));
        } else {
            System.out.println("Записали в память: ");
            list.add(new Value((String) args[0], (Integer) args[1], (Double) result));
        }

        return result;
    }

    public static Object cashingInFile(Object bean, Method method, Object[] args, Cache annotation, ObjectOutputStream oos) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        FileInputStream fis = new FileInputStream(CacheHandlerBeanPostProcessor.FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        argument = annotation.countArguments();
        isList = method.getReturnType() == List.class;
        List<String> list = new ArrayList<>();
        if (isList) {
            argument = 1;
        }
        while (fis.available() > 0) {
            Value value = (Value) ois.readObject();
            list.add(value.name);
            if (!isList)
                switch (argument) {
                    case 2:
                        if (value.equals(new Value((String) args[0], (Integer) args[1]))) {
                            System.out.println("Взяли из кеша файла:");
                            ois.close();
                            return value.result;
                        }
                    case 1:
                        if (value.name.equals(args[0])) {
                            System.out.println("Взяли из кеша файла:");
                            ois.close();
                            return value.result;
                        }
                }
        }
        for (String s : list) {
            if (s.equals(args[0])) {
                System.out.println("Взяли из кеша файла list:");
                return list;
            }
        }
        System.out.println("Записали в кеш файла:");
        Object result = method.invoke(bean, args);
        if (isList) {
            list = (List) result;
            oos.writeObject(new Value(list.get(list.size() - 1)));
        } else
            oos.writeObject(new Value((String) args[0], (Integer) args[1], (Double) result));
        return result;
    }
}
