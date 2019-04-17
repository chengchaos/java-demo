package cn.futuremove.mocktbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux()
public class MockTboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockTboxApplication.class, args);
	}

}
