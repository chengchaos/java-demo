package luxe.chaos;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.stream.Stream;

public class StreamingWcJavaApp {

    public static void main(String[] args) throws Exception {

        // step 1
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> streamSource = env.socketTextStream("localhost", 9999);

        streamSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String input, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] arrs = input.split("\\s+");
                Stream.of(arrs)
                        .filter(s -> s!= null&& s.length() > 0)
                        .forEach(s -> collector.collect(Tuple2.of(s, 1)));

            }
        })
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1)
                .print();

        env.execute("StreamingWcJavaApp");

    }
}
