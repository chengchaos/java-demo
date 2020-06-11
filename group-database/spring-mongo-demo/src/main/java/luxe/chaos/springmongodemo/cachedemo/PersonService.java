package luxe.chaos.springmongodemo.cachedemo;

import org.springframework.stereotype.Service;

import javax.cache.annotation.CacheResult;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2020/5/25 17:21 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@Service
public class PersonService {

//    @CacheResult(cacheName="people")
    public Person getPerson(int id) {
        Person person;
        switch (id) {
            case 1:
                person =  new Person(id, "Steve", "jobs");
                person.setEmail("chengchaos@gmail.com");

                List<ContactInfo> contactInfos = new ArrayList<>();
                ContactInfo c1 = new ContactInfo();
                c1.setAddress("竟园");
                c1.setEmail("chao.cheng@futuremove.cn");
                c1.setPhone("18514026899");

                contactInfos.add(c1);

                ContactInfo c2 = new ContactInfo();
                c2.setEmail("xwalker@163.com");
                c2.setAddress("清河");
                c2.setPhone("13331171727");

                contactInfos.add(c2);

                person.setContactInfoList(contactInfos);

                break;

            case 2:
                person =  new Person(id, "bill", "gates");
                break;

            default:
                person =  new Person(id, "unknown", "unknown");
        }

        return person;
    }
}
