package cn.chengchao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Shop {


    private static final Logger LOGGER = LoggerFactory.getLogger(Shop.class);

    private String name;

    public String getName() {
        return name;
    }

    public Shop() {
    }


    public Shop(String name) {
        this.name = name;
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private double calculatePrice(String product) {
        delay();
        Random random = new Random();
        int nextInt = random.nextInt(10);
        if (nextInt % 2 == 0) {
            throw new IllegalStateException("出错了...");
        }
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }


    private double calculatePrice1(String product) {
        delay();
        Random random = new Random();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public double getPrice(String product) {

        return calculatePrice1(product);
    }

    public String getPrice2(String product) {
        double price = calculatePrice1(product);

        Random random = new Random();
        Discount.Code code = Discount.Code.values() [random.nextInt(Discount.Code.values().length)];

        return String.format("%s:%.2f:%s", name, price, code);

    }

    public Future<Double> getPriceAsync1(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
           try {
               double price = calculatePrice(product);
               futurePrice.complete(price);
           } catch (Exception e) {
               futurePrice.completeExceptionally(e);
           }
        }).start();
        return futurePrice;
    }

    public Future<Double> getPriceAsync2(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }



    public void doSomethingElse() {
        LOGGER.info("doSomethingElse .. 执行更多任务，比如查询其他商店");
    }

}
