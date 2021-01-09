package luxe.chaos.netty.keepalive.server;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/8/2021 12:56 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Other {


    static class MyBean {

        private String title;

        public MyBean() {
            this.title = "Ms.";
        }

        public MyBean(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String name(String first, String last) {
            return first + " " + last;
        }

        public void printName(String name) {
            System.out.printf("name = %s %s%n", this.title, name);
        }

        public void printName(Supplier<String> supplier) {
            this.printName(supplier.get());
        }
    }

    public String concat(String a, String b, BinaryOperator<String> bo) {
        return bo.apply(a, b);
    }

    @Test
    public void test1() {

        String first = "Cheng";
        String last = "Chao";
        Supplier<MyBean> supplier = MyBean::new;
        MyBean mb = supplier.get();

        String name = mb.name(first, last);
        mb.printName(name);

        Function<String, MyBean> fn = MyBean::new;
        MyBean mb2 = fn.apply("先生");

        String name2 = concat(first, last, mb::name);
        mb2.printName(mb2::getTitle);

        IntStream.rangeClosed(2, 6)
                .forEach(System.out::println);

        List<String> stringList = IntStream.range(0, 5)
                .mapToObj(n -> n + ", ")
                .collect(Collectors.toList());
        System.out.println(stringList);
        ToIntFunction<String> tif = Integer::parseInt;
        System.out.println(tif.applyAsInt("12"));
    }

    public List<Integer> getList() {

        if (this.integerList == null) {
            System.out.printf("执行初始化%n");
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
            this.integerList = list;
        }
        return integerList;
    }

    private List<Integer> integerList;

    private volatile int theNum;

    public void list1() {
        List<Integer> list = getList();
        for (Integer integer : list) {
            this.theNum = integer;
        }
    }

    public void list2() {
        List<Integer> list = getList();
        list.forEach(i -> this.theNum = i);
    }


    @Test
    public void listTest() {

        int times = 50000;
        String message = "%s %d times using %d ms%n";
        for (long i = 0; i < times; i++) {
            list2();
        }
        for (long i = 0; i < times; i++) {
            list1();
        }
        Stopwatch stopwatch1 = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            list1();
        }
        stopwatch1.stop();

        Stopwatch stopwatch2 = Stopwatch.createStarted();
        for (long i = 0; i < times; i++) {
            list2();
        }
        stopwatch2.stop();

        System.out.printf(message, "list1", times, stopwatch1.elapsed(TimeUnit.MILLISECONDS));
        System.out.printf(message, "list2", times, stopwatch2.elapsed(TimeUnit.MILLISECONDS));
    }
}
