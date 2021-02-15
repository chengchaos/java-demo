package luxe.chaos.springboot.demo.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/7/2021 2:40 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class JacksonHelpeTest {

    private static final Logger logger = LoggerFactory.getLogger(JacksonHelpeTest.class);

    private void log(String input) {
        logger.info("input => {}", input);
    }

    private static class User {
        private int age;
        private String name;

        public User() {
            super();
        }

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void testEncode() {

        log("start main ...");
        new Thread(() -> log("run task 1..."))
                .start();
        new Thread(() -> log("run task 2 ..."))
                .start();
        log("end main ...");

    }

    static class UserContext implements AutoCloseable {
        static final ThreadLocal<User> ctx = new ThreadLocal<>();

        public UserContext(User user) {
            User u = new User();
            u.setAge(user.getAge());
            u.setName(user.getName());
            ctx.set(u);
        }

        @Override
        public void close() {
            ctx.remove();
        }

        public User getUser() {
            return ctx.get();
        }
    }

    public User changeAge(User user) {
        user.setAge(user.getAge() + 1);
        return user;
    }


    public void doRun(User user) {
        try (UserContext uc = new UserContext(user)) {
            User u = changeAge(uc.getUser());
            log(Thread.currentThread().getName() + " -> " + u.toString());
        }
    }

    @Test
    public void testUser1() {
        User user = new User();
        user.setAge(12);
        user.setName("Alice");


        new Thread(() -> doRun(user)).start();
        new Thread(() -> doRun(user)).start();
        new Thread(() -> doRun(user)).start();
        new Thread(() -> doRun(user)).start();

        try {

            Thread.currentThread().join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log("--end--");
    }

    @Test
    public void test3() {

        ObjectMapper om1 = new ObjectMapper();
        ObjectMapper om2 = new ObjectMapper();
        ObjectMapper om3 = new ObjectMapper();
        ObjectMapper om4 = new ObjectMapper();

        logger.info("{}\n, {}\n, {}\n, {}\n", om1, om2, om3, om4);
    }

    @Test
    public void test4() {
        User u1 = new User(12, "赵本山");
        User u2 = new User(23, "范伟");

        ExecutorService executor = new ThreadPoolExecutor(2, 4,
                5, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(16));

        for (int i = 0; i < 5; i++) {

            executor.submit(() -> {
                String x = JacksonHelper.toJson(u1).orElse("NULL");
                System.out.println(x);
            });
            executor.submit(() -> {
                String x = JacksonHelper.toJson(u2).orElse("NULL");
                System.out.println(x);
            });
        }

        try {
            Thread.currentThread().join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("executor shutdown => {}, terminated => {}",
                    executor.isShutdown(), executor.isTerminated());
            executor.shutdownNow();
            logger.info("executor shutdown => {}, terminated => {}",
                    executor.isShutdown(), executor.isTerminated());
        }
    }
}
