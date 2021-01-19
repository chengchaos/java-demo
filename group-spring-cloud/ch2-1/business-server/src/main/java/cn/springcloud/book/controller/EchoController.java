package cn.springcloud.book.controller;

import cn.springcloud.book.entities.DataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/15/2021 11:36 AM <br />
 * @see [相关类]
 * @since 1.0
 */
@RestController
public class EchoController {

    private static final Logger logger = LoggerFactory.getLogger(EchoController.class);

    @GetMapping(value = "/v1/echo")
    public DataWrapper<String> echo(@RequestParam(name = "q", defaultValue = "ok") String q) {
        logger.info("receive => {}", q);
        return DataWrapper.wrapData(q);
    }
}
