package com.example.myscala002.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/11 0011 下午 12:45 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class DemoNettyServer001 {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoNettyServer001.class);


    public void start() {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new LineBasedFrameDecoder(10000))
                        .addLast(new FirstServerHandler());

            }
        });


        ChannelFuture channelFuture = serverBootstrap.bind(9002);

        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>(){

            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    LOGGER.info("绑定端口成功");
                } else {
                    LOGGER.error("绑定端口失败");
                }
            }
        } );

    }

    private void someHandler(ServerBootstrap serverBootstrap ) {

        serverBootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                System.out.println("服务端启动中");
            }
        });

    }

    public static void main(String[] args) {

        new DemoNettyServer001().start();
    }
}
