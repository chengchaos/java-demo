package com.example.springakkademo.actor;

import java.util.concurrent.CompletableFuture;

public class CalcWrapper extends CalcRequest {

    private CompletableFuture<Integer> future = new CompletableFuture<>();


    public CompletableFuture<Integer> getFuture() {
        return future;
    }

    public CalcWrapper(Integer input) {
        super(input);
    }
}
