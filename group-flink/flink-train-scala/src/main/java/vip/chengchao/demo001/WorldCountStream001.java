package vip.chengchao.demo001;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import scala.xml.PrettyPrinter;

public class WorldCountStream001 {


    public static void main(String[] args) throws Exception {

        int port = 0;
        ParameterTool tool = ParameterTool.fromArgs(args);
        port = tool.getInt("port", 9999);
        String host = tool.get("host", "localhost");

        // 1. 获取执行上下文
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 2. 读取数据。
        DataStreamSource<String> dss = env.socketTextStream(host, port);

        // 3. transform
        dss.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
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
        }).keyBy(0)
                .timeWindow(Time.seconds(5L))
                .sum(1)
                .print()
                .setParallelism(1)
        ;

        env.execute("WorldCountStream001");
    }
}
