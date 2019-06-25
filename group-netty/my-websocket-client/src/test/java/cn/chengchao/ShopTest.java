package cn.chengchao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ShopTest {



    private static final Logger LOGGER = LoggerFactory.getLogger(ShopTest.class);


    private static final String product = "iPhoneXS";

    @Test
    public void test1() {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        int nextInt = new Random().nextInt(10);

        Future<Double> futurePrice = null;
        if (nextInt %2 == 0) {
            futurePrice = shop.getPriceAsync1(product);
        } else {
            futurePrice = shop.getPriceAsync2(product);
        }
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        // 执行更多任务，比如查询其他商店
        shop.doSomethingElse();
        // 在计算商品价格的同时
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }


    private List<String> findPrices1(List<Shop> shops, final String product) {

        return shops
                //.stream()
                .parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)) )
                .collect(toList());
    }


    private List<Shop> getShops() {

        return Arrays.asList(
                new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("myFavoriteShop"),
                new Shop("BuyItAll")
        );
    }
    @Test
    public void testList() {



        List<String> prices = findPrices1(getShops(), product);

        prices.forEach(System.out::println);
    }

    @Test
    public void testCompletableFuture() {


        List<CompletableFuture<String>> priceFuture = getShops().stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f",
                                shop.getName(),
                                shop.getPrice("iPhoneXS"))))
                .collect(toList());

        List<String> prices = priceFuture.stream()
                .map(CompletableFuture::join)
                .collect(toList());

        prices.forEach(System.out::println);
    }

    @Test
    public void testQuotePrices() {
        long start = System.nanoTime();

        List<String> prices = getShops().stream()
                .map(shop -> shop.getPrice2(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
        System.out.println("size = "+ prices.size());
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }



    public final Executor executor = Executors.newFixedThreadPool(Math.min(getShops().size(), 100), new ThreadFactory() {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    @Test
    public void testQuotePricesAsync() {
        long start = System.nanoTime();

        List<CompletableFuture<String>> priceFutures = getShops().stream()
                // 以异步方式获取每个 Shop 中指定商品的价格, 返回一个 future .
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice2(product), executor))
                // 如果有返回值, 转换成 Quote 对象
                .map(future -> future.thenApply(Quote::parse))
                // 用另一个异步任务计算折扣 ...
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
                .collect(toList());

        // 流程中的所有 Future 执行完毕,
        // 提取各自的返回值
        List<String> prices = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println("size = "+ prices.size());
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }
}
