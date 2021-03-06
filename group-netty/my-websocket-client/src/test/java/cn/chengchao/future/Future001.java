package cn.chengchao.future;

import akka.parboiled2.RuleTrace;
import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchao - 2018-12-17 21:10 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class Future001 {

    private Double doSomeLongComputation() {

        try {
            TimeUnit.SECONDS.sleep(2L);
            return 42D;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0D;
    }

    private void doSomethingElse() {
        System.out.println("hai ...");
    }


    private void waitSometime(long t) {
        try {
            TimeUnit.SECONDS.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testFuture5() {

        ExecutorService executor = Executors.newCachedThreadPool();

        Future<Double> future = executor.submit(
                new Callable<Double>() {
                    @Override
                    public Double call() throws Exception {
                        return doSomeLongComputation();
                    }
                }
        );

        boolean done = future.isDone();

        doSomethingElse();
        System.out.println("is done ==> "+ done);

        try {
            Double result = future.get(3L, TimeUnit.SECONDS);
            Double expected = 42D;
            Assert.assertEquals("不相等", expected, result);
            System.out.println("result ==> "+ result);
            done = future.isDone();
            boolean cancelled = future.isCancelled();

            System.out.println("is done ==> "+ done +", is cancelled ==> "+ cancelled);

            TimeUnit.SECONDS.sleep(5L);
        } catch (ExecutionException ee) {
            /* 计算抛出一个异常 */
        } catch (InterruptedException ie) {
            /* 被中断异常 */
        } catch (TimeoutException te) {
            /* Future 完成前超时 */
        }
    }

    @Test(expected=ExecutionException.class)
    public void testGetAndJoin() throws Exception {
        CompletableFuture<Integer> future= CompletableFuture.supplyAsync(
                () -> {
                    /* join 时，在这里抛出异常 */
                    int i = 1/0;
                    return 100;
                });
        /* get 时， 在这里抛出异常 */
        future.get();
        Integer joinResult = future.join();
        System.out.println("result = "+ joinResult);

    }

    @Test
    public void testAccept() throws Exception {

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            return 100;
        });
        CompletableFuture<Void> f =  future2.thenAcceptAsync(i -> {
            this.waitSometime(1L);
            System.out.println(i); // (2) 100
        });
        System.out.println("f is done ==> "+ f.isDone()); // (1) f is done ==> false
        System.out.println(f.get()); // (3) null
        System.out.println("f is done ==> "+ f.isDone()); // (4) f is done ==> true
        this.waitSometime(2L);
    }

    /**
     * 抛出异常是返回 -1， 否则返回 100
     */
    @Test
    public void testEx() {

        final int x = new Random().nextInt(2);
        CompletableFuture<Integer> future= CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("stmt = 1 / "+ x);
                    int i = 1/x;
                    return 100;
                })
                .exceptionally((t) ->  -1);

        try {
            Integer result = future.get();
            System.out.println("result ==> "+ result);
            waitSometime(2L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("-- end --");

    }


    @Test
    public void test01() {
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            System.out.println("hello1");
        });

        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            return "hello2";
        });

        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("hello3");
            return "hello3";
        });

        try {
//            f1.get();
            String s = f2.get();
            System.out.println(s);
            f3.completeExceptionally(new Exception("kao"));
            System.out.println(f3.get());
            boolean world = f3.complete("World");
            boolean world2 = f3.complete("World2");
            System.out.println(world);
            System.out.println(f3.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * thenApply 的功能相当于将 CompletableFuture<T> 转换成 CompletableFuture<U>
     */
    @Test
    public void test002() {

        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenApply(input -> input + " world")
                        .thenApply(String::toUpperCase);

        CompletableFuture<Double> f2 =
                CompletableFuture.supplyAsync(() -> "10")
                        .thenApply(Integer::parseInt)
                        .thenApply(i -> i * 10.0D);
        try {
            String output = future.get();
            System.out.println(output);
            Double d = f2.get();
            System.out.println(d);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * thenCompose 可以用于组合多个 CompletableFuture
     * 将前一个结果作为下一个计算的参数.
     * <p>
     * 它们之间存在着先后顺序.
     * </p>
     */
    @Test
    public void test003() {

        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> "hello")
                        .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " world"));

        CompletableFuture<Double> f2 =
                CompletableFuture.supplyAsync(() -> "10")
                        .thenCompose(s -> CompletableFuture.supplyAsync(() -> Double.parseDouble(s)))
                        .thenCompose(d -> CompletableFuture.supplyAsync(() -> d * 10));

        try {
            System.out.println(future.get()); // hello world
            System.out.println(f2.get()); // 100.0
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test()
    public void thenComposeTest() {

        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> "hello")
                        .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " world"));

        int i = 50;
        try {
        CompletableFuture<Void> f2 =
                CompletableFuture.supplyAsync(() -> "10")
                        .thenCompose(s -> CompletableFuture.supplyAsync(() -> {
                            if (i > 100) {
                                throw new RuntimeException("10 错误");
                            }
                            return Double.parseDouble(s);
                        }))
                        .thenCompose(d -> CompletableFuture.supplyAsync(() -> {
                            if (i > 10) {
                                throw new RuntimeException("double 错误");
                            }
                            return d * 10;
                        }))
                        .exceptionally(ex -> {
                            System.err.println("kao222");
//                            ex.printStackTrace();
                            return null;
                        }).thenAccept(d -> {
                    System.out.println(">> " + d);
                });


            System.out.println(future.get());
//            System.out.println(f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException | CompletionException e) {
            System.err.println("kao");
            e.printStackTrace();
        }

    }

    /**
     * 使用 thenCombine() 之后
     * f1 和 f2 之间是并行的.
     * 这一点和 thenCompose 方法不同.
     */
    @Test
    public void combinTest() {
        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> "100");
        CompletableFuture<Integer> f2 =
                CompletableFuture.supplyAsync(() -> 10);

        CompletableFuture<Double> f3 =
                f1.thenCombine(f2, (s, i) -> Double.parseDouble(s) * i);


        try {
            Double aDouble = f3.get();
            System.out.println(aDouble);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void combineTest() {
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(4L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });

        CompletableFuture<String> f3 = f1.thenCombine(f2, (s, i) -> {
            return s + " : " + i;
        });

        String s = null;
        try {
            s = f3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(s);
    }

    /**
     * thenAcceptBoth 和 thenCombin 类似
     * 只是返回的是 CompletableFuture<Void> 类型
     */
    @Test
    public void acceptBothTest() {

        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> "100");

        CompletableFuture<Integer> f2 =
                CompletableFuture.supplyAsync(() -> 10);

        CompletableFuture<Void> f3 =
                f1.thenAcceptBothAsync(f2, (s, i) -> System.out.println(Double.parseDouble(s) * i));


        try {
            f3.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void completeTest() {
        CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s + " world")
                .thenApply(s -> s + "\nThis is CompletableFuture demo.")
                .thenApply(String::toLowerCase)
                .whenComplete((result, throwable) -> {
                    System.out.println(throwable);
                    System.out.println(result);

                });
    }


    @Test
    public void handleTest() {
        CompletableFuture<String> handle = CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s + " world")
                .thenApply(s -> s + "\nThis is CompletableFuture demo.")
                .thenApply(String::toLowerCase)
                .handle((result, throwable) -> {
                    if (result != null) {
                        return result;
                    } else {
                        return "kao";
                    }
                });
        try {
            System.out.println(handle.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void actionTest() {

        int r = 0; //new Random().nextInt(4);
        System.out.println(" r = "+ r);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> {
                    if (r == 0) throw new RuntimeException("r == 0");
                    return s + " world";
                })
                .exceptionally(t -> "0")
                .thenApply(s -> {
                    if (r == 1) throw new RuntimeException("r == 1");
                    return s + "\nThis is CompletableFuture demo.";
                })
                .exceptionally(t -> "1")
                .thenApply(s -> {
                    if (r == 2) throw new RuntimeException("r == 2");
                    return s.toUpperCase();
                })
                .exceptionally(t -> {
                    System.out.println(t.getMessage());
                    return null;
                })
                .thenAccept(System.out::println);
    }

    /**
     * Either 表示的是两个 CompletableFuture
     *
     * 当其中任何一个计算完成都会执行.
     *
     * 和它类似的是 applyToEither .
     */
    @Test
    public void eitherTest() {

        CompletableFuture<String> f1 =
                CompletableFuture.supplyAsync(() -> "10");
        CompletableFuture<String> f2 =
                CompletableFuture.supplyAsync(() -> "20");

        CompletableFuture<Void> f3 = f1.acceptEither(f2,
                System.out::println);
    }
}
