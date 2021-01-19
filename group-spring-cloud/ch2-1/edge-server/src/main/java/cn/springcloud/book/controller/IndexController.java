package cn.springcloud.book.controller;

import cn.springcloud.book.entities.DataWrapper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/13/2021 4:18 PM <br />
 * @since 1.0
 */
@RestController
public class IndexController {

    @ApiOperation("Ping 接口")
    @GetMapping(value = "/v1/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Map<String, String> ping() {
        return Collections.singletonMap("PONG",
                LocalDateTime.now().toString());
    }


    @PostMapping(value = "/v1/user-log-in")
    public DataWrapper<String> userLogin(@RequestBody Map<String, String> body) {

        String name = body.getOrDefault("username", "");
        String pass = body.getOrDefault("password", "");

        if (StringUtils.isAnyBlank(name, pass)) {
            return DataWrapper.wrapError("username / password can not be empty.");
        }

        String key = UUID.randomUUID().toString();

        return DataWrapper.wrapData(key);

    }
}
