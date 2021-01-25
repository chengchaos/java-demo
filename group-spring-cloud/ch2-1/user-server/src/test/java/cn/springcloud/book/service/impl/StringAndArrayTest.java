package cn.springcloud.book.service.impl;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 1/21/2021 10:17 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class StringAndArrayTest {

    @Test
    public void testString2array() {

        String str = "1234abcd";
        char[] arr = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.println("i = "+ arr[i]);
        }
        char[] arr2 = {'1', '2', '3', '4', 'a', 'b', 'c', 'd' };
        System.out.println("Arrays.equals(arr, arr2) => " + Arrays.equals(arr, arr2));
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testArray2String() {

        char[] arr = {'a', 'b', 'c', 'd' };
        String str = String.copyValueOf(arr, 1, 2);
        System.out.println(str);
    }
}
