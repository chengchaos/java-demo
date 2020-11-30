package luxe.chaos.springmongodemo.controller;

import luxe.chaos.springmongodemo.entity.MyData;
import luxe.chaos.springmongodemo.entity.User;
import luxe.chaos.springmongodemo.repos.MyDataRepository;
import luxe.chaos.springmongodemo.repos.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/7/5 00:26 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@RestController
public class TestMongoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMongoController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MyDataRepository myDataRepository;


    @PostMapping("/v1/mongo/test/save")
    public String save() {

        LOGGER.info("start running ...");

        User user = new User();
        user.setAge(44);
        Calendar c = Calendar.getInstance();
        c.set(1973, 11, 1);

        user.setBirthday(c.getTime());
        user.setUserName("程超");
        user.setPassword("over2012");

        this.userRepository.saveUser(user);

        MyData myData = new MyData();
        myData.setVin("abcde");
        myData.setUploadTime(2020L);
        myData.setInfo("你好");

        this.myDataRepository.insert(myData);

        return "OK\n";

    }
}
