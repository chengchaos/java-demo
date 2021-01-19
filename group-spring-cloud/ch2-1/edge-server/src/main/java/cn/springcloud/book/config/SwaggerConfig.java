package cn.springcloud.book.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * <p>
 * <strong>
 * Swagger Java 配置
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/18/2021 10:31 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.springcloud.book.controller"))
                .paths(Predicates.or(PathSelectors.ant("/v1/user/add"),
                        PathSelectors.ant("/v1/user/find/*")))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Spring Boot 项目集成 Swagger 实例文档",
                "我的博客： https://www.chaos.luxe/ 欢迎大家访问。",
                "API v1.0",
                "Teams of service",
                new Contact("红の蜻蜓", "https://www.chaos.luxe/", "chengchaos@outlook.com"),
                "Apache", "http://www.apache.org/", Collections.emptyList());

    }
}
