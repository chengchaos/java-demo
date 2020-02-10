package luxe.chaos;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class User {

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public static void main(String[] args) {
        var user = new User();
        String code1 = """
                <body>
                    <div>Hello , world</div>
                </body>
                """;

        logger.info(code1);

        var x = new Random().nextInt(10);

        var y = switch (x) {
            case 1, 3, 5, 7, 9:
                yield "1";
            case 2:
                yield "2";
            default:
                yield "other";

        };
        logger.info("y = {}", y);

    }
}
