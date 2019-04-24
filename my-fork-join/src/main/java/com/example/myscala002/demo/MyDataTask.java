package com.example.myscala002.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/20 0020 下午 2:24 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class MyDataTask extends RecursiveTask<MyDataResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyDataTask.class);

    private DataService dataService = new DataService();

    private String vin;

    private Integer scop;

    private List<String> vins;
    private List<Integer> scops;

    public MyDataTask(List<String> vins, List<Integer> scops) {
        this.vins = vins;
        this.scops = scops;

        if (vins.size() == 1) {
            this.vin = vins.get(0);
        }
        if (scops.size() == 1) {
            this.scop = scops.get(0);
        }
    }

    @Override
    protected MyDataResult compute() {

        List<RecursiveTask<MyDataResult>> forks = new ArrayList<>();

        if (Objects.isNull(vin)) {
            LOGGER.info("vin ==> {}, scop ==> {}", vin, scop);
            for (String vin1 : vins) {
                MyDataTask task = new MyDataTask(Collections.singletonList(vin1), scops);
                forks.add(task);
                task.fork();
            }
        }

        else if (Objects.isNull(scop)) {
            LOGGER.info("vin ==> {}, scop ==> {}", vin, scop);
            for (Integer scop1 : scops) {
                MyDataTask task = new MyDataTask(Collections.singletonList(vin), Collections.singletonList(scop1));
                forks.add(task);
                task.fork();
            }
        }

        else {
            LOGGER.info("vin ==> {}, scop ==> {}", vin, scop);
            List<MyData> myDatas = dataService.getByVin(vin, scop);
            MyDataResult result = new MyDataResult(vin, scop, myDatas);

            LOGGER.info("result ==> {}", result);
            return result;
        }

        MyDataResult result = MyDataResult.NULL;

        for (RecursiveTask<MyDataResult> fork : forks) {
            result = result.merge(fork.join());
            LOGGER.info("result ==> {}", result);
        }

        return result;
    }

    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();

        List<String> vins = Arrays.asList("vin-11", "vin-13");
        List<Integer> scops = Arrays.asList(3, 9);

        MyDataTask myDataTask = new MyDataTask(vins, scops);

        MyDataResult invoke = pool.invoke(myDataTask);



        final Map<String, Map<Integer, List<List<MyData>>>> dataMap = invoke.getDataMap();

        //MyDataResult join = myDataTask.join();

        //dataMap = join.getDataMap();
        for (String vin : dataMap.keySet()) {
            System.out.println("==> "+ vin);
            Map<Integer, List<List<MyData>>> subMap = dataMap.get(vin);
            for (Integer scop : subMap.keySet()) {
                System.out.println("=== ==> "+ scop);
                List<List<MyData>> subList = subMap.get(scop);
                for (List<MyData> list : subList) {
                    for (MyData mydata : list) {
                        System.out.println("=== === ===> "+ mydata);
                    }
                }
            }
        }


        System.err.println("===== ");

        dataMap.keySet()
                .stream()
                .map(dataMap::get)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .forEach(System.out::println);



        System.err.println("===== ");

        vins.stream()
                .map(dataMap::get)
                .forEach(scopeMap ->
                        scops.stream()
                                .map(scopeMap::get)
                                // Stream<List<MyData>>
                                .flatMap(Collection::stream)
                                // List<MyData>
                                .flatMap(Collection::stream)
                                .forEach(System.out::println));


        System.err.println("===== ");

        scops.stream()
                .forEach(scope ->
                    vins.stream()
                            .map(dataMap::get)
                            .map(subMap -> subMap.get(scope))
                            .flatMap(List::stream)
                            .flatMap(List::stream)
                            .forEach(System.out::println));

    }
}
