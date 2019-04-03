package com.example.springakkademo.actor;

import java.io.Serializable;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;

public class CalcRequest implements Serializable {

    private static final AtomicLong index = new AtomicLong(0L);

    private static long getSerialNo() {

        return index.incrementAndGet();
    }

    private long id = getSerialNo();

    private Integer input;

    public long getId() {
        return id;
    }

    public Integer getInput() {
        return input;
    }

    public CalcRequest(Integer input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CalcRequest.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("input=" + input)
                .toString();
    }
}
