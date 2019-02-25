package cn.chengchaos;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/1/25 0025 下午 5:11 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class FutureDemo003 {



    public List<String> findPrices(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f",
                        shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }


    @Test
    public void test() {

        List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                new Shop("LetsSaveBig"),
                new Shop("MyFavoriteShop"),
                new Shop("BuyItAll"));

        Executor executor =
                Executors.newFixedThreadPool(Math.min(shops.size(), 100),
                        new ThreadFactory() {
                            @Override
                            public Thread newThread(Runnable r) {
                                Thread t = new Thread(r);
                                t.setDaemon(true);
                                return t;
                            }
                        });

        String product = "myPhone4s";
        //findProces(shops, "myPhone4s");

        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(()
                        -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
                .collect(toList());


        List<String> collect = priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());


    }
}
