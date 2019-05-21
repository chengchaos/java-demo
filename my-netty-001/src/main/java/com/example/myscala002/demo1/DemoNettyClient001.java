package com.example.myscala002.demo1;

import com.example.netty.demo2.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/11 0011 下午 1:31 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class DemoNettyClient001 {


    private static final Logger LOGGER = LoggerFactory.getLogger(DemoNettyClient001.class);

    private static final int MAX_RETRY = 5;

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry) {

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                LOGGER.info("连接成功!");
            } else if (retry == 0) {
                LOGGER.warn("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                LOGGER.warn("{} : 连接失败，第 {} 次重连……", new Date(), order);
                bootstrap.config()
                        .group()
                        .schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }


    public void start(int inetPort) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        // 1: 指定线程模型
        bootstrap.group(workerGroup);

        // 2: 指定 IO 类型为 NIO
        bootstrap.channel(NioSocketChannel.class);

        // 3: IO 处理逻辑
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

                ch.pipeline()
//                        .addLast(new LineBasedFrameDecoder(10000))
//                        .addLast(new FirstClientHandler())
                    .addLast(new ClientHandler())
                ;
            }
        });

        ChannelFuture future = bootstrap.connect("localhost", inetPort);

        // 4：建立连接
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("连接成功");
                } else {
                    LOGGER.info("连接失败");
                    LOGGER.info("future ==> {}", future);
                }
            }
        });
    }


    public static void main(String[] args) {
        new DemoNettyClient001().start(9092);
    }
}
