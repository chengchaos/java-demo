package luxe.chaos.webflux.demo.common;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 3:58 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class WebClientTests {


    private static final Logger logger = LoggerFactory.getLogger(WebClientTests.class);

    @Test
    public void test0001() {
        //
        WebClient webClient = WebClient.create();

        Mono<String> stringMono = webClient.get()
                .uri("https://www.chaos.luxe")
                .retrieve()
                .bodyToMono(String.class);

        String result = stringMono.block();
        logger.info("result => {}", result);
        stringMono.subscribe(s -> logger.info("received -> {}", s));
    }

    @Test
    public void testUseBaseUrl() {
        String baseUrl = "https://www.chaos.luxe";
        WebClient webClient = WebClient.create(baseUrl);

        webClient.method(HttpMethod.GET)
                .uri(uriBuilder ->
                        uriBuilder.scheme("https")
//                                .host("www.chaos.luxe")
                                .path("/random-password.html")
                                .port("443")
                                .queryParam("q", "a")
                                .build());

        String result = webClient.get().retrieve()
                .onStatus(hs -> !hs.is2xxSuccessful(), resp -> {
                            logger.info("resp -> {}", resp);
                            return Mono.error(new RuntimeException("kao"));
                        }
                )
                .bodyToMono(String.class)
                .block();
        logger.info("receive -> {}", result);
//                .subscribe(s -> logger.info("receive -> {}", s));
    }

    @Test
    public void testForm() {

        WebClient webClient = WebClient.create("https://www.chaos.luxe");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "chengchao");
        map.add("password", "P@ssw0rd!@#");

        WebClient.ResponseSpec responseSpec = webClient
                .post()
                .uri("/sign-in")
                .body(Mono.just(map), Map.class)

                .retrieve();
        Mono<String> mono = responseSpec.onStatus(s -> !s.is2xxSuccessful(), resp -> {
            logger.warn("resp 状态码: {}, reason phrase: {}", resp.statusCode().value(),
                    resp.statusCode().getReasonPhrase());
            return Mono.error(new RuntimeException("我去！"));
        }).bodyToMono(String.class)
                .doOnError(WebClientResponseException.class, err -> {
                    logger.warn("出错了 ### ... {}, -> {}",
                            err.getRawStatusCode(),
                            err.getResponseBodyAsString());
                    throw new RuntimeException(err.getMessage());
                })
//                .onErrorReturn("Fallback")
                ;

        logger.info("result: {}", mono.block());

    }
}
