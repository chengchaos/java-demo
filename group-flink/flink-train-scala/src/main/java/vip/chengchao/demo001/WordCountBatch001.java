package vip.chengchao.demo001;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Use Java API
 */
public class WordCountBatch001 {

    public static void main(String[] args) throws Exception {
        String input = "C:\\works\\git-repo\\github.com\\chengchaos\\java-demo\\group-flink\\flink-train-scala\\src\\main\\resources\\hello.txt";
        // 1. 获取上下文。
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 2. 都数据
        DataSource<String> text = env.readTextFile(input);

        // 3. 执行转换
        AggregateOperator<Tuple2<String, Integer>> sum = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                if (s != null && s.length() > 0) {
                    String[] arr = s.split("\\s+");
                    if (arr.length > 0) {
                        for (String str : arr) {
                            collector.collect(new Tuple2<>(str, 1));
                        }
                    }
                }
            }
        }).groupBy(0).sum(1);


        sum.print();


    }
}
