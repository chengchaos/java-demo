package com.example.myjsp.service.impl;

import com.example.myjsp.bean.MyName;
import com.example.myjsp.bean.Name;
import com.example.myjsp.bean.Result;
import com.example.myjsp.bean.ResultCode;
import com.example.myjsp.service.SomeThingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/29 0029 下午 4:15 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
@Service
public class SomeThingServiceImpl implements SomeThingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomeThingServiceImpl.class);

    @Autowired
    @Qualifier(value = "loadBalanced")
    private RestTemplate loadBalanced;

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "someRestFallback",
            ignoreExceptions = {IllegalArgumentException.class})
    @Override
    public Result<Map<String, Object>> someRest(MyName myName) {

        String url = "http://my-scala002/v1/say-hello?name={name}";

        Map<String, Object> uriVariables = Collections.<String, Object>singletonMap("name", myName.getName());

        URI uri = UriComponentsBuilder.fromUriString(url)
                .build()
                .expand(uriVariables)
                .encode()
                .toUri();
        LOGGER.info("uri ==> {}", uri);

        Map<String, Object> data = this.loadBalanced.getForObject(uri, Map.class);

        return Result.success(data);
    }

    public Result<Map<String, Object>> someRestFallback(MyName name, Throwable throwable) {

        LOGGER.error("someRestFallback ", throwable);

        return Result.fail(ResultCode.INTERNAL_CIRCUIT_BREAKE_ERROR);
    }
}
