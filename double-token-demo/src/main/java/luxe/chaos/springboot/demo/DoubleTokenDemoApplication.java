package luxe.chaos.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DoubleTokenDemoApplication {

    public static void main(String[] args) {
        //SpringApplication.run(DoubleTokenDemoApplication.class, args)
        SpringApplication sa = new SpringApplication(DoubleTokenDemoApplication.class);
        sa.setWebApplicationType(WebApplicationType.REACTIVE);
        ConfigurableApplicationContext context = sa.run(args);
        System.out.println("Running .... "+ context);
    }

}
