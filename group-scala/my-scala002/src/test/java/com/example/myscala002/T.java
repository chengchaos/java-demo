package com.example.myscala002;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/25 0025 上午 11:38 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class T {


    class Value {
        public Value(String province) {
            this.province = province;
        }

        private String province;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }


    @Test
    public void t2() {

        List<Value> list = Arrays.asList(new Value("a"), new Value("b"));

        Map<String, List<Map<String, Object>>> collect = list.stream()
                .map(value -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("PROVINCE", value.getProvince());
                    return data;
                })
                .collect(Collectors.groupingBy(data -> data.get("PROVINCE").toString()));

        collect.entrySet().stream().forEach(entry -> System.out.println(entry.getValue().getClass()));
    }
}
