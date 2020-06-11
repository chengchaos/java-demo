package luxe.chaos.springmongodemo.repos;

import luxe.chaos.springmongodemo.entity.MyData;
import luxe.chaos.springmongodemo.entity.User;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Optional;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/23 17:30 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@SpringBootTest
public class UserRepositoryTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private MyDataRepository myDataRepository;

    @Test
    void saveUser() {
        User user = new User();
        user.setAge(44);
        Calendar c = Calendar.getInstance();
        c.set(1973, 11, 1);

        user.setBirthday(c.getTime());
        user.setId("chengchao-29731101");
        user.setUserName("程超");
        user.setPassword("over2012");

        this.userRepository.saveUser(user);

        MyData myData = new MyData();
        myData.setId("abcde_2020");
        myData.setVin("abcde");
        myData.setUploadTime(2020L);
        myData.setInfo("你好");

        this.myDataRepository.insert(myData);


    }

    @Test
    void findByName() {

        User user = this.userRepository.findByName("cheng, chao");
        LOGGER.info("user => {}", user);

        MyData myData = this.myDataRepository.getById("abcde_2020");
        LOGGER.info("mydata => {}", myData);
        Assertions.assertNotNull(myData, "kao");
    }

    @Test
    void findById() {
        String id = "chengchao-29731101";

        Optional<Document> opt = this.userRepository.findById(id);

        if (opt.isPresent()) {
            Document doc = opt.get();
            doc.forEach((s, o) -> LOGGER.info("{} => {}", s, o));
        }

    }

    @Test
    void update() {

        String id = "chengchao-29731101";
        Optional<Document> opts = this.userRepository.findById(id);

        if (opts.isPresent()) {
            Document doc = opts.get();
            User user = new User();
            user.setId(id);
            user.setUserName(doc.get("userName", String.class) + "(程超)");
            user.setPassword("over2019");
            long update = this.userRepository.update(user);
            LOGGER.info("update => {}", update);
        }

    }

    @Test
    void delete() {
    }
}
