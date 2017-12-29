package com.sbt.javaschool.serialization.impl;

import com.sbt.javaschool.serialization.api.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {
    private List<String> list = new ArrayList<>();

    @Cache(countArguments = 1,cashType = CacheType.FILE)
    public double doHardWork(String name, Integer value) {
        return (double) value;
    }

    @Override
    @Cache(cashType = CacheType.FILE)
    public List<String> doHardWork(String name) {
        list.add(name);
        return list;
    }
}
