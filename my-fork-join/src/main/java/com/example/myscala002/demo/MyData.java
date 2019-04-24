package com.example.myscala002.demo;

import java.util.StringJoiner;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/20 0020 下午 2:44 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class MyData {

    private String vin;
    private int value;

    private int scop;

    public String getVin() {
        return vin;
    }

    public int getValue() {
        return value;
    }

    public int getScop() {
        return scop;
    }

    static MyData valueOf(String vin, int scop, int value) {
        MyData data = new MyData();
        data.vin = vin;
        data.scop = scop;
        data.value = value;

        return data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MyData.class.getSimpleName() + "[", "]")
                .add("vin='" + vin + "'")
                .add("value=" + value)
                .add("scop=" + scop)
                .toString();
    }
}
