package cn.springcloud.book;

import cn.springcloud.book.config.ProjectConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EdgeServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EdgeServerApplication.class, args);

        ProjectConfig pc = context.getBean(ProjectConfig.class);
        System.err.println(pc.isInspectOnStart());
    }

}
