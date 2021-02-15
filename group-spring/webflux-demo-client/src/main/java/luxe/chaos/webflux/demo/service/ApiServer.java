package luxe.chaos.webflux.demo.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * <strong>
 *     服务器相关信息
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 10:24 AM <br />
 * @since 1.0
 */
@Target(ElementType.TYPE) // 放到 Class 上面
@Retention(RetentionPolicy.RUNTIME) // 运行时得到
public @interface ApiServer {

    String value() default "http://127.0.0.1:8080";
}
