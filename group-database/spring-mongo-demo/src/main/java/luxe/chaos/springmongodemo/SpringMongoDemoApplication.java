package luxe.chaos.springmongodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
//@EnableCaching
public class SpringMongoDemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringMongoDemoApplication.class, args);
	}

}
