package cn.chengchaos;

import java.util.concurrent.CompletableFuture;

public class Test {


    @org.junit.Test
    public void test() throws Exception {

        CompletableFuture<String> future = new CompletableFuture<>();

        future.complete("haha");
        future.obtrudeValue("hehe");

        String s = future.get();
        System.out.println(s);
    }
}
