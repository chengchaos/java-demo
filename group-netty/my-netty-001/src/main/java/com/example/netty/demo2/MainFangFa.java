package com.example.netty.demo2;

import com.example.myscala002.demo1.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 2:23 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class MainFangFa {



    private static final Logger LOGGER = LoggerFactory.getLogger(MainFangFa.class);

    public static void start(int inetPort) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        // 1: 线程模型
        bootstrap.group(group);

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {

                ch.pipeline()
//                            .addLast(new LineBasedFrameDecoder(10000))
//                            .addLast(new FirstClientHandler())
                        .addLast(new ClientHandler())
                ;
            }
        });

        ChannelFuture future = bootstrap.connect("localhost", inetPort);

        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("连接成功");
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
                } else {
                    LOGGER.info("连接失败");
                    LOGGER.info("future ==> {}", future);
                }
            }
        });


    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    LOGGER.info("输入消息发送至服务器端：");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();

                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);

                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), packet);
                    channel.writeAndFlush(byteBuf);


                }
            }
        }).start();

    }

    public static void main(String[] args) throws InterruptedException {

        start(9092);
    }
}
