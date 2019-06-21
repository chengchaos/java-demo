package com.example.codec;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/10 0010 下午 2:24 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class BytesTest {


    @Test
    public void test1() {

        byte b1 = (byte) 0b1111_1111;
        byte b2 = (byte) 0xFF;
        byte b3 = (byte) 255;

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3 == b2);
        System.out.println(b1 == b2);

    }


    @Test
    public void test2() {

        int[] arr1 = {1, 2, 3, 4};

        int[] arr2 = new int[4];

        System.arraycopy(arr1, 0, arr2, 0, arr2.length);

        System.out.println(Arrays.toString(arr2));

        int i = 8;

        int j = i << 1;

        System.out.println(" j = "+j);

    }
}
