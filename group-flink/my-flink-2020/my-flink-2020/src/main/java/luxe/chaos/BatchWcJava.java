package luxe.chaos;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.stream.Stream;

/**
 * 使用 Java API 开发 Flink 的
 * 批处理应用程序
 */
public class BatchWcJava {


    public static void main(String[] args) throws Exception {

        // 1, 获取环境
        ExecutionEnvironment env = ExecutionEnvironment.createCollectionsEnvironment();
        String input = "file:///E:\\sinogold\\docs";

        // step2: 读入数据
        DataSource<String> txtDs = env.readTextFile(input);

        // step3: 应用操作 transfrom

        txtDs.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (line, collector) -> {

            String[] tokens = line.toLowerCase().split("\\s+");
            Stream.of(tokens)
                    .filter(s -> s != null && s.length() > 0)
                    .forEach(s -> collector.collect(Tuple2.of(s, 1)));

        }).groupBy(0)
                .sum(1)
                .print();


    }
}
