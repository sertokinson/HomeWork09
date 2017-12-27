package com.sbt.javaschool.serialization.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cash {
    int countArguments() default 2;
    CashType cashType() default CashType.MEMORY;
}
