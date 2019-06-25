package cn.chengchao;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.stream.ActorMaterializer;
import akka.stream.IOResult;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class TestStreams {

    private static final ActorSystem system;

    private static final Materializer materializer;

    static {
        system = ActorSystem.create("system");
        materializer = ActorMaterializer.create(system);
    }

    public void waiting(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test001() {

        /*
         * range 创建了 1 到 5 的整数 Source 对象, 作为数据的来源和流的起点.
         * 第一个泛型表示他产生数据的类型
         * 第二个泛型表示运行时产生的其他辅助数据.
         * 没有则为 NotUsed.
         */
        Source<Integer, NotUsed> source = Source.range(1, 5);

        /*
         * Sink : 操作 Source 产生的数据.
         *
         */
        Sink<Integer, CompletionStage<Done>> sink = Sink.foreach(
                System.out::println
        );

        /*
         * source.to 方法将 Source 和 Sink 连接起来, 并创建换一个 RunnableGraph 对象
         */
        RunnableGraph<NotUsed> graph = source.to(sink);

        /*
         * RunnableGraph 的 run 方法需要传入一个 Materializer 对象
         * 它主要给流分配 Actor 并驱动其执行.
         */
        graph.run(materializer);
    }

    /**
     * 从集合中构建 Source
     */
    @Test
    public void test002() {

        List<String> list = Arrays.asList("sh", "bj", "nj");

        Source<String, NotUsed> source = Source.from(list);
        /*
         * runForeach 实际是对 runWith(Sink.foreach(f), materializer)
         * 的包装.
         */
        source.runForeach(System.out::println, materializer);
    }


    /**
     * 从 Future 中构建 Source
     */
    @Test
    public void test003() {

        Source<String, NotUsed> source1 = Source.fromFuture(Futures.successful("Hello Akka!"));

        source1.runForeach(System.out::println, materializer);


        Source<String, NotUsed> source2 = Source.repeat("Hello");
        source2.limit(5)
                .runForeach(System.out::println, materializer);

        Source<ByteString, CompletionStage<IOResult>> source3 = FileIO
                .fromPath(Paths.get("e:/demo_in.txt"));

        source3.limit(5)
                .runForeach(System.out::println, materializer);

        this.waiting(5L);
    }


    /**
     * 构建 Sink
     */
    @Test
    public void testSink001() {

        /* 使用 sink 循环每个元素 */
        Sink<Integer, CompletionStage<Done>> sink1 = Sink.foreach(System.out::println);

        /* 使用 Sink 做 fold 运算 */
        Sink<Integer, CompletionStage<Integer>> sink2 = Sink
                .fold(1, (x, y) -> x * y);

        CompletionStage<Integer> r1 = Source.range(1, 5)
                .runWith(sink2, materializer);
        r1.thenAccept(System.out::println);

        /*
         * 使用 Sink 做 reduce 运算
         * 和 flod 类似, 不过没有初始参数
         */

        Sink<Integer, CompletionStage<Integer>> sink3 = Sink.reduce((x, y) -> x + y);
        CompletionStage<Integer> r3 = Source.range(1, 5)
                .runWith(sink3, materializer);
        r3.thenAccept(System.out::println);

        /* 使用 FileIO */

        Sink<ByteString, CompletionStage<IOResult>> sink4 = FileIO
                .toPath(Paths.get("e:/demo_in.txt"));


    }

    /**
     * 构建 Flow
     * Flow 需要一个输入和一个输出, 一般作为中间过程而存在
     *
     * Flow 组件通过调用 Source.via 方法附加在 Source 上
     * 此时会构建出一个新的 Source 对象.
     *
     * 也可以通过调用 Flow.to 方法附加在 Sink 上,
     * 此时会构建出一个新的 Sink 对象.
     *
     */
    @Test
    public void testFlow001() {

        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6");

        Flow<String, Integer, NotUsed> flow = Flow.of(String.class)
                .map(x -> Integer.parseInt(x) * 3);

        Sink<Integer, CompletionStage<Done>> sink = Sink.foreach(System.out::println);

        Source.from(list)
                .via(flow)
                .runWith(sink, materializer);

        System.out.println("=============");
        Source.from(list)
                .runWith(flow.to(sink), materializer);

    }

    @Test
    public void testFlow002() {
        Flow<String, Integer, NotUsed> flow =
                Flow.fromSinkAndSource(
                        Sink.foreach(System.out::println)
                        , Source.range(1, 3)
                );

        System.out.println(flow);
    }
}