package luxe.chaos.webflux.demo.handlers;

import luxe.chaos.webflux.demo.ex.CheckException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/4/2021 5:57 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Component
@Order(-2) // 数值越小，优先级越高
public class ExceptionHandler implements WebExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        // 设置一个响应头 400
        ServerHttpResponse response =  serverWebExchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.APPLICATION_PROBLEM_JSON);

        String errorMsg = this.getErrorMsg(throwable);
        DataBuffer buffer = response.bufferFactory()
                .wrap(errorMsg.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }

    private String getErrorMsg(Throwable ex) {

        logger.debug("ex => {}", ex.getClass().getSimpleName());
        // 已知异常
        if (ex instanceof CheckException) {
            CheckException e = (CheckException) ex;
            return e.getFieldName() + ":invalid value " + e.getFieldValue();
        }

        // 未知异常
        else {
            ex.printStackTrace();
            return ex.toString();
        }

    }
}
