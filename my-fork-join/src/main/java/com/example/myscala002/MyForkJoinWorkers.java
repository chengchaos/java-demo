package com.example.myscala002;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/19 0019 下午 7:18 <br />
 * @since 1.1.0
 */
public class MyForkJoinWorkers {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyForkJoinWorkers.class);

    /**
     * RecursiveTask 是同步的
     */
    private static class Worker extends RecursiveTask<Integer> {


        private static final long serialVersionUID = 1L;

        private static final int THRESHOLD = 10;

        private int fromIndex;
        private int toIndex;
        private Coin[] bag;
        private Balance balance;

        public Worker(int fromIndex, int toIndex, Coin[] bag, Balance balance) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.bag = bag;
            this.balance = balance;
        }

        @Override
        protected Integer compute() {

            if (toIndex - fromIndex < THRESHOLD) {



                List<Integer> list = new ArrayList<>();
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++) {
                    if (balance.weigh(bag, i)) {
                        count ++;
                        list.add(i);
                    }
                }
                LOGGER.info("f ==> {}, t ==> {}; ==> {}", fromIndex, toIndex, count);

                LOGGER.info("list ==> {}", list);
                return count;
            }
            else {
                int mid = (fromIndex + toIndex) / 2;
                Worker left = new Worker(fromIndex, mid, bag, balance);
                Worker right = new Worker(mid + 1, toIndex, bag, balance);

                invokeAll(left, right);

                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ForkJoinPool pool = new ForkJoinPool(32);

        Coin[] bag = Coin.fullBag();

        //Process process = new CoinProcess();

        Balance balance = new Balance.DefaultBalance();

        long start1 = System.currentTimeMillis();

        Worker workers = new Worker(0, bag.length -1, bag, balance);

        pool.invoke(workers);

        System.err.println("Task is runnint ... 1");
        Thread.sleep(2);
        System.err.println("Task is runnint ... 2");

        System.out.println("fake coin: " + workers.join() + ", time : " + (System.currentTimeMillis() - start1));



        while (!workers.isDone()) {

            showLogs(pool);
            TimeUnit.MILLISECONDS.sleep(100);
        }


        long start2 = System.currentTimeMillis();

        List<Integer> list = new ArrayList<>();

        int count = 0;
//        for (int i = 0; i < bag.length; i++) {
//
//            if (balance.weigh(bag, i)) {
//                count ++;
//                list.add(i);
//            }
//        }


        System.out.println("fake coin: " + count + ", time : " + (System.currentTimeMillis() - start2));

        System.out.println("list ==> "+ list);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
        showLogs(pool);
    }

    private static void showLogs(ForkJoinPool pool) {

        LOGGER.info("线程池大小 ==> {}", pool.getPoolSize());
        LOGGER.info("当前执行任务的线程数量 ==> {}", pool.getActiveThreadCount());
        LOGGER.info("getRunningThreadCount ==> {}", pool.getRunningThreadCount());
        LOGGER.info("getQueuedSubmissionCount ==> {}", pool.getQueuedSubmissionCount());
        LOGGER.info("getQueuedTaskCount ==> {}", pool.getQueuedTaskCount());
        LOGGER.info("getStealCount ==> {}", pool.getStealCount());
        LOGGER.info("isTerminated ==> {}", pool.isTerminated());
        LOGGER.info("{}", "************************");
    }
}
