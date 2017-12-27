package com.sbt.javaschool.serialization.impl;

import com.sbt.javaschool.serialization.api.Service;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Main.run(context.getBean(Service.class));
    }
    public static void run(Service service){
        double r1 = service.doHardWork("work1", 10);
        double r2 = service.doHardWork("work1", 5);
        double r3 = service.doHardWork("work2", 10);

    }
}
