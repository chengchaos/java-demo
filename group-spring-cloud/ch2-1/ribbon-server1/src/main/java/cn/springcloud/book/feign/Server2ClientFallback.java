package cn.springcloud.book.feign;

import org.springframework.stereotype.Component;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said. <br />
 *
 * Spring Cloud Feign就是通过Fallback实现的，有两种方式：
 * <ul>
 *   <li>1、@FeignClient.fallback = UserFeignFallback.class 指定一个实现Feign接口的实现类。</li>
 *   <li>2、@FeignClient.fallbackFactory = UserFeignFactory.class 指定一个实现
 *  {@code FallbackFactory<T>} 工厂接口类</li>
 * </ul>
 *
 * 因为Fallback是通过Hystrix实现的， 所以需要开启Hystrix，spring boot application.properties文件配置feign.hystrix.enabled=true，这样就开启了Fallback
 * </p>
 *
 * @author Cheng, Chao - 1/5/2021 3:47 PM <br />
 * @since 1.0
 */
@Component
public class Server2ClientFallback implements Server2Client {
    @Override
    public String add(int a, int b) {
        return "this was writed by Fallback";
    }
}
