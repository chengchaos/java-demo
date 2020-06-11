package luxe.chaos.springmongodemo.controller;

import com.alibaba.fastjson.JSON;
import luxe.chaos.springmongodemo.cachedemo.Person;
import luxe.chaos.springmongodemo.cachedemo.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/25 17:31 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */


@RequestMapping("/person")
@RestController
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @RequestMapping("/get")
    public Person getPerson(int id) {
        Person person = personService.getPerson(id);
        LOGGER.info("读取到数据 {}•{}", person.getFirstName(), person.getLastName());
        return person;
    }


    @GetMapping("/v1/test-get")
    public Map<String, Object> doGet(HttpServletRequest request) {

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String header = request.getHeader(name);
            LOGGER.info("{} => {}", name, header);
        }

        return Collections.singletonMap("message", "success");
    }

    @PostMapping(value = "/v1/test-post"
//    consumes = "application/x-www-form-urlencoded;charset=UTF-8"
//    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public ResponseEntity<String> doPost(@RequestBody Map<String, Object> form) {


        LOGGER.info("form => {}", form);

        form.keySet().forEach(k -> LOGGER.info("{} => {}", k, form.get(k)));
//        ResponseEntity<String> re = new ResponseEntity(json, headers, HttpStatus.OK);
        String info = "OK 成功了！";
//        String json = JSON.toJSONString(data);
        Result<?> result = Result.successWithData(info);
        JSON.toJSONString(result);
//      UriComponentsBuilder.fromUriString("/v1/test-post").build().toUri()
        return ResponseEntity.ok()
                //.contentLength(json.length())
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJsonString())
                ;

    }

}



