package cn.chengchaos;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class JClientTest {


    JClient client = new JClient("127.0.0.1:2552");

    @Test
    public void test() throws Exception {

        client.set("123", "123");
        Object result = null;
        result = client.get("123")
                .toCompletableFuture()
                .get(5L, TimeUnit.SECONDS);

        System.err.println("result = "+ result);

        Integer ri = Integer.valueOf(result.toString());

        System.err.println("ri = " + ri);


        TimeUnit.SECONDS.sleep(2L);


    }
}
