package com.example.myscala002.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/20 0020 上午 10:59 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class DataService {


    private List<MyData> data = new ArrayList<>(1000);

    public DataService() {

        String[] vins = new String[10];

        for (int i = 11; i <= 20; i++) {
            String vin = "vin-" + i;
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    data.add(MyData.valueOf(vin, j, k));
                }
            }
        }
    }

    public List<MyData> getByVin(String vin, int scop) {

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.data.stream()
                .filter(data -> data.getVin().equals(vin))
                .filter(data -> data.getScop() == scop)
                .collect(Collectors.toList());
    }

    public List<MyData> take10() {

        return this.data.stream().limit(10).collect(Collectors.toList());
    }


    public static void main(String[] args) {

        List<MyData> list = new DataService().take10();

        System.out.println(list);

    }
}

