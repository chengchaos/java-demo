package luxe.chaos.tcd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/4/2021 1:31 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class PingController {

    @GetMapping("/v1/ping")
    public Map<String, Object> ping() {
        return Collections.singletonMap("message", "PANG");
    }
}
