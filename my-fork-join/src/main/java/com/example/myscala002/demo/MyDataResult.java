package com.example.myscala002.demo;

import java.util.*;

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
public class MyDataResult {
    public static final MyDataResult NULL = new MyDataResult();

    private Map<String, Map<Integer, List<List<MyData>>>> dataMap;

    private MyDataResult() {

    }

    public MyDataResult(String vin, Integer scop, List<MyData> myDatas) {

        List<List<MyData>> subList = new ArrayList<>();
        Map<Integer, List<List<MyData>>> subMap = new HashMap<>();
        subMap.put(scop, subList);
        dataMap = new HashMap<>();
        dataMap.put(vin, subMap);

        subList.add(myDatas);
    }


    public MyDataResult merge(MyDataResult join) {

        if (this == NULL) return join;
        if (join == NULL) return this;

        Map<String, Map<Integer, List<List<MyData>>>> rightDataMap = join.getDataMap();
        for (String vin : rightDataMap.keySet()) {
            Map<Integer, List<List<MyData>>> leftSubMap = this.dataMap.get(vin);
            if (Objects.isNull(leftSubMap)) {
                this.dataMap.put(vin, rightDataMap.get(vin));
            } else {
                Map<Integer, List<List<MyData>>> rightSubMap = rightDataMap.get(vin);
                for (Integer scope : rightSubMap.keySet()) {
                    leftSubMap.put(scope, rightSubMap.get(scope));
                }
            }

        }

        return this;
    }

    public Map<String, Map<Integer, List<List<MyData>>>> getDataMap() {
        return dataMap;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", MyDataResult.class.getSimpleName() + "[", "]");

        for (String vin : dataMap.keySet()) {
            for (Integer scop : dataMap.get(vin).keySet()) {
                joiner.add("vin=" + vin);
                joiner.add("scop=" + scop);
            }
        }


        return joiner.toString();
    }
}
