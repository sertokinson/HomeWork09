package com.sbt.javaschool.serialization.impl;

import com.sbt.javaschool.serialization.api.Service;

public class ServiceImpl implements Service {
    @Cash(countArguments = 1,cashType = CashType.FILE)
    public double doHardWork(String name, Integer value) {
        return (double) value;
    }
}
