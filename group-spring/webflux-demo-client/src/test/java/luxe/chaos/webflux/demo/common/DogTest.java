package luxe.chaos.webflux.demo.common;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 10:48 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class DogTest {


    @Test
    public void testShout1() {

        ConstructorAccess<Dog> ca = ConstructorAccess.get(Dog.class);
        Dog dog = ca.newInstance();
        dog.setName("京东");
        MethodAccess ma = MethodAccess.get(Dog.class);
        String result = (String) ma.invoke(dog, "shout");

        System.out.println(result);
    }
}
