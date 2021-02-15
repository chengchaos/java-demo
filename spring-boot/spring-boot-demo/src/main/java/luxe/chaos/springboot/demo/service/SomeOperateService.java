package luxe.chaos.springboot.demo.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/7/2021 3:49 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Service
public class SomeOperateService {

    public String getInfo() {

        int r = ThreadLocalRandom.current().nextInt(50);

        try {
            TimeUnit.MILLISECONDS.sleep( 1000L + r);
            return "等待了大约 ... " + (1000 + r) + " 毫秒";
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        return "异常了";
    }
}
