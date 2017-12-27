package com.sbt.javaschool.serialization.impl;

import java.util.Objects;

public class Value {
    String name;
    Integer value;
    Double result;

    public Value(String name, Integer value, Double result) {
        this.name = name;
        this.value = value;
        this.result = result;
    }

    public Value(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Value(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value value1 = (Value) o;
        return Objects.equals(name, value1.name) &&
                Objects.equals(value, value1.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, value);
    }
}
