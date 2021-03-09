package luxe.chaos.springboot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/9/2021 3:07 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @GetMapping("/v1/ping")
    public String ping(String q) {
        logger.info("receive => {}", q);
        return "Pone";
    }

    @GetMapping(value = "/v2/ping", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<String> ping2(String q) {
        return Mono.just("Pong");
    }
}
