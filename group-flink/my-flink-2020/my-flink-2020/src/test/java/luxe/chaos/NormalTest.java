package luxe.chaos;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalTest {

    private String getName() {
        int i = 5;
        int x = new Random().nextInt(10);
        if (x > i) {
            return null;
        } else {
            // String.valueOf(x).return
            return String.valueOf(x);
        }
    }

    @Test
    public void test1() {

        /*
         * 先输入 "name".var
         * 会撑场变量
         */
        String name = "name";
        System.out.println(String.format("name = $s", name));

        name = getName();
        /*
         * 输入 name.null
         * 或者 name.notnull || name.nn
         */
        System.out.println("user.null");
        if (name == null) {
            System.out.println("null");
        }

        System.out.println("name.notnull");
        if (name != null) {
        }

        System.out.println("name.nn");
        if (name != null) {
        }

        List<Integer> list = new ArrayList<>();

        list.add(1);

        /*
         * 遍历 List :
         * list.for
         * list.fori // 带 i 遍历
         */
        for (Integer integer : list) {

        }


        boolean bl = true;

        // bl.not
        if (!bl) {

        }
        // bl == true.if
        if (bl == true) {

        }

        // xyz.cast
        // xyz.castvar
        Object xyz = "xyz";
        String xyz2 = ((String) xyz);

        String s = (String) xyz;


    }
}



