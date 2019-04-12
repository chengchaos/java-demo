package com.example.myscala002.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.SocketHandler;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/12 0012 下午 3:45 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class DemoNettyServer002 {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoNettyServer002.class);


    public void start() {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap()
                    .group(bossGroup, workGroup);

            server.channel(NioServerSocketChannel.class);


            server.childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {

                    ch.pipeline()
                            .addLast(new LineBasedFrameDecoder(1000))
                            .addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


                                    if (msg instanceof ByteBuf) {

                                        ByteBuf buf = (ByteBuf) msg;
                                        String str = buf.toString(CharsetUtil.UTF_8);
                                        LOGGER.info("Server receive : {}", str);
                                    }


                                }
                            });
                }
            });


            ChannelFuture channelFuture = server.bind(9092).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if (future.isSuccess()) {
                        LOGGER.info("sucess");
                    } else {
                        LOGGER.warn("failure");
                    }
                }
            })
                   .sync()
            ;
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) { // NOSONAR
            forInterrupedException(e);

        } finally {
            try {
                bossGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) { // NOSONAR
                forInterrupedException(e);
            }

            try {
                workGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) { // NOSONAR
                forInterrupedException(e);
            }
        }
    }

    private static void forInterrupedException(InterruptedException e) {
        LOGGER.warn("InterruptedException :{}", e.getMessage());
        Thread.currentThread().interrupt();
    }

    public static void main(String[] args) {
        new DemoNettyServer002().start();
    }
}
