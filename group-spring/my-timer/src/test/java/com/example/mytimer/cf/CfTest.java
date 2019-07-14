package com.example.mytimer.cf;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class CfTest {
    
    
    @Test
    public void test0() {
        
        CompletableFuture<Integer> cf = new CompletableFuture<>();

        int x = new Random().nextInt(10);

        int y = x % 2;

        
        Integer res;
        try {

            new Thread(() -> {

                if (y == 0) {
                    cf.complete(42);
                } else {
                    cf.completeExceptionally(new RuntimeException("我去"));
                }
            }).start();
            res = cf.get();

            System.out.println("res = "+ res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
    }

}
