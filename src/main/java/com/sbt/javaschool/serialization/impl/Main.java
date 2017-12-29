package com.sbt.javaschool.serialization.impl;

import com.sbt.javaschool.serialization.api.Service;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Main.run(context.getBean(Service.class));
    }
    public static void run(Service service){
        System.out.println(service.doHardWork("work1", 10));
        System.out.println(service.doHardWork("work1", 5));
        System.out.println(service.doHardWork("work2", 10));
        System.out.println(service.doHardWork("work3"));
        System.out.println(service.doHardWork("work3"));
        System.out.println(service.doHardWork("work3"));
        System.out.println(service.doHardWork("work2"));
        System.out.println(service.doHardWork("work3"));
    }
}
