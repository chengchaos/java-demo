package luxe.chaos.helper.unsafe;

import com.alibaba.fastjson.JSONObject;
import luxe.chaos.helper.unsafe.SimpleJson;
import org.junit.Test;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2020/11/30 23:33 <br />
 * @see [ 相关类方法 ]
 * @since [ 产品模块版本 ]
 */
public class SimpleJsonTest {

    @Test
    public void test1() {
        String input2 = "{\"user\": \"Admin\", \"money\": 123.344, \"age\": 18, \"test\":  {\"name\" : \"chengchao\", \"mail\" : {\"first\" : \"hello@163.com\"}}}";

        SimpleJson simpleJson = SimpleJson.valueOf(input2);
        System.out.println(simpleJson);

        JSONObject jsonObject = JSONObject.parseObject(input2);
        System.out.println(jsonObject);

        simpleJson
                .entrySet()
                .forEach(System.out::println);

        JSONObject jsonObject1 = JSONObject.parseObject(simpleJson.getString("test").get());
        System.out.println(jsonObject1);

        SimpleJson simpleJson1 = SimpleJson.valueOf(simpleJson.getString("test").get());
        System.out.println(simpleJson1);
    }
}
