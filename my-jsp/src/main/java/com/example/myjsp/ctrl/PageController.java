package com.example.myjsp.ctrl;

import com.example.myjsp.bean.MyName;
import com.example.myjsp.bean.Result;
import com.example.myjsp.bean.ResultCode;
import com.example.myjsp.service.SomeThingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/25 0025 下午 4:38 <br />
 * @since 1.1.0
 */
@Controller
public class PageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);

    @Autowired
    @Qualifier(value="loadBalanced")
    private RestTemplate loadBalanced;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SomeThingService someThingService;

    @RequestMapping(value = {"/","/index"})
    @HystrixCommand(fallbackMethod = "indexFallback")
    public String index(Map<String, Object> model){

        Assert.notNull(loadBalanced, "没有注入 RestTemplate");

        Map<String, Object> uriVariables = Collections.<String, Object>singletonMap("name", "chengchao");

        String url = "http://my-scala002/v1/say-hello?name={name}";
        URI uri = UriComponentsBuilder.fromUriString(url)
                .build()
                .expand(uriVariables)
                .encode()
                .toUri();
        LOGGER.info("uri ==> {}", uri);

        @SuppressWarnings("unchecked")
        Map<String, Object> result = this.loadBalanced.getForObject(uri, Map.class);

        LOGGER.info("result ==> {}", result);

        if (result != null) {
            model.putAll(result);
        }

        LOGGER.info("model ==> {}", model);

        return "index";
    }

    public String indexFallback(Map<String, Object> models) {

        models.put("username", "UNKNOW");
        return "index";
    }



    @GetMapping(value="/baidu")
    @HystrixCommand(fallbackMethod = "baiduFallback")
    @ResponseBody
    public String bauidu() {

        return restTemplate.getForObject("https://1111", String.class);

    }

    @ResponseBody
    public String baiduFallback(Throwable throwable) {

        LOGGER.error("", throwable);

        return "kao";

    }

    @GetMapping(value="/v1/some-rest")
    @ResponseBody
    public Map<String, Object> someRest(String name) {

        Result<Map<String, Object>> result = this.someThingService.someRest(new MyName(name));

        if (result != null) {

            if (result.getCode().equals(ResultCode.SUCCESS.getCode())) {
                return result.getData();
            } else if (result.getCode().equals(ResultCode.INTERNAL_CIRCUIT_BREAKE_ERROR.getCode())) {
                LOGGER.error(result.getMessage());
            }
        }


        return Collections.<String, Object>emptyMap();
    }


}
