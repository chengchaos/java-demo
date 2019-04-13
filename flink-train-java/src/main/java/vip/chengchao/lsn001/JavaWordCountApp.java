package vip.chengchao.lsn001;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.nio.file.Paths;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchao - 2019-04-13 12:36 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class JavaWordCountApp {


    public static void main(String[] args) throws Exception {
        String input = Paths.get("src/test/resources/wc.txt")
                .toUri().toString();

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> text = env.readTextFile(input);

        text.print();

        FlatMapOperator<String, Tuple2<String, Integer>> stringTuple2FlatMapOperator = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase()
                        .split("\\s");
                for (String token : tokens) {
                    if (token.length() > 0) {
                        collector.collect(new Tuple2<>(token, 1));
                    }
                }
            }

        });

        AggregateOperator<Tuple2<String, Integer>> sum = stringTuple2FlatMapOperator.groupBy(0).sum(1);


        sum.print();


    }
}
