package luxe.chaos.webflux.demo;

import luxe.chaos.webflux.demo.service.ProxyCreator;
import luxe.chaos.webflux.demo.service.UserServiceApi;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WebfluxDemoApplication {

    @Bean
    public FactoryBean<UserServiceApi> factoryBean(ProxyCreator proxyCreator) {
        return new FactoryBean<>() {
            /**
             *
             * @return 接口对象实例
             * @throws Exception ex
             */
            @Override
            public UserServiceApi getObject() throws Exception {
                return (UserServiceApi) proxyCreator.createProxy(this.getObjectType());
            }

            /**
             * @return 返回接口类型
             */
            @Override
            public Class<?> getObjectType() {
                return UserServiceApi.class;
            }
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(WebfluxDemoApplication.class, args);
    }

}
