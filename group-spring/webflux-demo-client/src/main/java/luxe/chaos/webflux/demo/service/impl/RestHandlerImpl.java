package luxe.chaos.webflux.demo.service.impl;

import luxe.chaos.webflux.demo.service.MethodInfo;
import luxe.chaos.webflux.demo.service.RestHandler;
import luxe.chaos.webflux.demo.service.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.client.WebClient.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 1:23 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Component
public class RestHandlerImpl implements RestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestHandlerImpl.class);


    private WebClient webClient;

    @Override
    public void init(ServerInfo serverInfo) {
        logger.debug("server info => {}", serverInfo);
        webClient = create(serverInfo.getUrl());
        logger.debug("WebClient => {}", webClient);

    }

    @Override
    public Object invokeRest(ServerInfo serverInfo, MethodInfo methodInfo) {

        logger.debug("method info => {}", methodInfo);
        RequestBodySpec requestBodySpec = this.webClient
                .method(methodInfo.getHttpMethod())
                .uri(methodInfo.getUrl(), methodInfo.getArgs())
                .accept(MediaType.APPLICATION_JSON);

        ResponseSpec retrieve;

        if (methodInfo.getBody() != null) {
            retrieve = requestBodySpec
                    .body(methodInfo.getBody(), methodInfo.getBodyElementType())
                    .retrieve();
        } else {
            retrieve = requestBodySpec.retrieve();
        }
        // 异常
        retrieve.onStatus(httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND),
                response -> Mono.just(new RuntimeException("Not Found")))
                .onStatus(httpStatus -> httpStatus.equals(HttpStatus.BAD_REQUEST),
                        response -> Mono.just(new RuntimeException("Bad Request")))
        ;

        if (methodInfo.isFlux()) {
            return retrieve.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            return retrieve.bodyToMono(methodInfo.getReturnElementType());
        }
    }
}
