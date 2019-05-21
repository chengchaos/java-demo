package com.example.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
 * @author chengchaos[as]Administrator - 2019/4/15 0015 下午 2:55 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class MainFangFa {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainFangFa.class);


    public void start() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workGroup);

            serverBootstrap.channel(NioServerSocketChannel.class);

            serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new LifeCycleTestHandler())
                            .addLast(new PacketDecoder())
                            .addLast(ChannelHandlers.inA())
                            .addLast(ChannelHandlers.inB())
                            .addLast(ChannelHandlers.inC())
                            .addLast(ChannelHandlers.outA())
                            .addLast(ChannelHandlers.outB())
                            .addLast(ChannelHandlers.outC())
                            .addLast(new LoginRequestHandler())
                            .addLast(new MessageRequestHandler())
                            .addLast(new ServerHandler())
                    ;
                }
            });

            ChannelFuture future = serverBootstrap.bind(9092);

            ChannelFuture sync = future.channel().closeFuture().sync();


        } finally {

            workGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }


    public static void main(String[] args) throws Exception {

        new MainFangFa().start();
    }
}
