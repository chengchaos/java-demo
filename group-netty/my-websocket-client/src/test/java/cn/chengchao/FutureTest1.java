package cn.chengchao;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;

public class FutureTest1 {


    private static final Logger LOGGER = LoggerFactory.getLogger(FutureTest1.class);

    private static Double getDoubleResult() throws InterruptedException {
        Random random = new Random();
        int r = random.nextInt(5);
        LOGGER.info("r = {}", r);
        TimeUnit.SECONDS.sleep(r);

        double res = random.nextDouble();
        LOGGER.info("res = {}", res);
        return res;
    }




    private static ExecutorService executor = Executors.newCachedThreadPool();

    public Double run() {

        Future<Double> future = executor.submit(FutureTest1::getDoubleResult);

        System.out.println("===================");

        try {
            Double result = future.get(1, TimeUnit.SECONDS);
            return result + 1;
        } catch (ExecutionException ee) {
            LOGGER.error("ee >> ", ee);
        } catch (InterruptedException ie) {
            LOGGER.error("ie >> ", ie);
        } catch (TimeoutException te) {
            LOGGER.error("te >> ", te);
        }

        return null;
    }


    @Test
    public void testFuture1() {

        FutureTest1 ft = new FutureTest1();

        Double d = ft.run();

        System.out.println(d);
    }
}
