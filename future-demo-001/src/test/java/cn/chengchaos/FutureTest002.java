package cn.chengchaos;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureTest002 {

    private static final Logger LOGGER = LoggerFactory.getLogger(FutureTest002.class);

    private void waitAMoment(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void exceptionTest1() throws ExecutionException, InterruptedException {

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {

            this.waitAMoment(1L);
            long curr = System.currentTimeMillis();
            if (curr > 10000) {
                throw new RuntimeException("kao");
            }
            return curr + "Hehe";
        }).exceptionally(t -> {
            LOGGER.error("ex on f1: ", t);
            return System.currentTimeMillis() + "";
        });

        CompletableFuture<Long> f2 = f1.thenApply((str) -> {
            this.waitAMoment(1L);
            return Long.valueOf(str, 10);
        })
        .exceptionally(t -> {
            LOGGER.error("ex on f2: ", t);
            return System.currentTimeMillis();
        });

        CompletableFuture<Date> f3 = f2.thenApply(d -> {
            this.waitAMoment(1L);
            return new Date(d);
        })
        .exceptionally(t -> {
            // 异常处理可以接收到前面任意个 Future 抛出的异常。召唤
            LOGGER.error("ex on f3: ", t);
            Date date = new Date();
            System.err.println(date);
            return date;
        });


        Date date = f3.get();

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

        this.waitAMoment(4L);
        System.out.println("-- end --");


    }


    @Test
    public void exceptionTest2() throws ExecutionException, InterruptedException {

        CompletableFuture<Date> f1 = CompletableFuture.supplyAsync(() -> {
                    this.waitAMoment(1L);
                    long curr = System.currentTimeMillis();
//                    if (curr > 10000) {
//                        throw new RuntimeException("kao");
//                    }
                    return curr + "";
                }).exceptionally(t -> {
                    LOGGER.error("ex on f1: ", t);
                    return System.currentTimeMillis() + "";
                })
                .thenCompose(str -> CompletableFuture.supplyAsync(() -> {
                    this.waitAMoment(1L);
                    return Long.valueOf(str, 10);
                }).exceptionally(t -> {
                    LOGGER.error("ex on f2: ", t);
                    return System.currentTimeMillis();
                }))
                .thenCompose(d -> CompletableFuture.supplyAsync(() -> {
                    this.waitAMoment(1L);
                    return new Date(d);
                }).exceptionally(t -> {
                    // 异常处理可以接收到前面任意个 Future 抛出的异常。召唤
                    LOGGER.error("ex on f3: ", t);
                    Date date = new Date();
                    System.err.println(date);
                    return date;
                }));


        Date date = f1.get();

        System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

        this.waitAMoment(4L);
        System.err.println("-- end --");


    }
}
