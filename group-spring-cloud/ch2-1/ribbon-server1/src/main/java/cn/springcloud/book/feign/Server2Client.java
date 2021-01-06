package cn.springcloud.book.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * <strong>
 *   FeignClient 注解使用说明
 * </strong><br /><br />
 * As the title said.
 * </p>
 * <p>
 *
 * name ： 指定 FeignClient 的名称，如果使用了 Ribbon，name 回作为微服务的名称用于服务发现。<br />
 * url： 一般用于调试，可以手动指定 FeignClient 的调用地址。<br />
 * decode404： 当 404 时，如果该字段为 true，会调用 decoder 进行解码，否则抛出 FeignException。<br />
 * configuration: Feign 的配置类，可以自定义 Feign 的 Encoder, Decoder, LogLevel, Contract 。<br />
 * fallback: 定义容错处理类。当调用远程接口失败，会调用相应的容错逻辑。指定的类必须实现接口。<br />
 * fallbackFactory: 工厂类，用于生成 fallback 类的实例。<br />
 * path: 定义当前 FeignClient 的统一前缀。<br />
 * </p>
 *
 * @author Cheng, Chao - 1/4/2021 5:58 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@FeignClient(name="ribbon-server-2", fallback = Server2ClientFallback.class)
public interface Server2Client {

    @GetMapping(value = "/add")
    String add(@RequestParam int a, @RequestParam int b);
}
