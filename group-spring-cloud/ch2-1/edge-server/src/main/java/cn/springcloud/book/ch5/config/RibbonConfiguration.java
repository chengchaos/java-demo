package cn.springcloud.book.ch5.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {



    /**
     * 随机策略
     *
     * 随机选择 server
     * @return
     */
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }

    public IRule roundRobinRule() {
        return new RoundRobinRule();
    }
}
