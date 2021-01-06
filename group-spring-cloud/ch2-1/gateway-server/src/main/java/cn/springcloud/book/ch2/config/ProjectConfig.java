package cn.springcloud.book.ch2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/5/2021 9:05 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class ProjectConfig {

    private static final Logger logger = LoggerFactory.getLogger(ProjectConfig.class);

    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        logger.info("init builder => {}", builder);

        return builder.routes()
                .route(r -> r.path("/ribbon-server-1/**")
                        .filters(f -> f.rewritePath("/ribbon-server-1/(?<segment>.*)", "/$\\{segment}"))
                        .uri("http://192.168.56.1:18081/")
                        .id("jd_route"))
                .build();
    }
}
