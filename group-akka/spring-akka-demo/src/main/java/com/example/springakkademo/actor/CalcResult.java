package com.example.springakkademo.actor;

import java.io.Serializable;
import java.util.StringJoiner;

public class CalcResult implements Serializable {

    private long id;

    private Integer result;

    public CalcResult(long id, Integer result) {
        this.id = id;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public Integer getResult() {
        return result;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", CalcResult.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("result=" + result)
                .toString();
    }
}
