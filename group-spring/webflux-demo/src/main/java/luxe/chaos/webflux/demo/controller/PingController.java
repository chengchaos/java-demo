package luxe.chaos.webflux.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/3/2021 4:31 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @GetMapping(value = "/v1/ping1")
    public String ping1() {
        return "Pong";
    }

    @GetMapping(value = "/v1/ping2")
    public Mono<String> ping2() {

        long begin = System.currentTimeMillis();
        Mono<String> result = Mono.defer(() -> {
            int r = ThreadLocalRandom.current().nextInt(1000);
            try {
                TimeUnit.MILLISECONDS.sleep(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            logger.info("defer = {}", (System.currentTimeMillis() - begin));
            return Mono.just("Pong2");
        });
        logger.info("ping2 = {}", (System.currentTimeMillis() - begin));
        return result;
    }


    @GetMapping(value = "/v1/ping3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ping3() {

        return Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            return "Item " + i;
        }));
    }
}
