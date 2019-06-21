package vip.chengchao.lsn002;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author chengchao - 2019-04-13 13:34 <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
public class StreamingWordCountJavaApp {

    public static void main(String[] args) throws Exception {

        int port = 0;

        try {
            /*
             * --key1 value1 --key2 value2 -key3 value3
             */
            ParameterTool tool = ParameterTool.fromArgs(args);
            port = tool.getInt("port");
        } catch (Exception e) {
            e.printStackTrace();
            port = 9999;
        }
        // 获取流执行环节:
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> text = env.socketTextStream("localhost", port);

        text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] strings = s.toLowerCase().split("\\s");
                for (String word : strings) {
                    if (word.length() > 0) {
                        collector.collect(Tuple2.of(word, 1));
                    }
                }
            }
        })
        .keyBy(0)
        .timeWindow(Time.seconds(5))
        .sum(1)
        .print()
        .setParallelism(1)
        ;


        env.execute("StreamingWordCountJavaApp");
    }

}
