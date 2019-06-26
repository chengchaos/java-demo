package com.example.myscala002.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueChannelOption;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/10 0010 下午 3:21 <br />
 * @since 1.1.0
 */
public class EchoServer {


    private static final Logger LOGGER = LoggerFactory.getLogger(EchoServer.class);

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {

       int port = 7777;

       new EchoServer(port).start();
    }


    public void someInfo() {

        Map<String, String> map = System.getenv();
        for(Iterator<String> itr = map.keySet().iterator(); itr.hasNext();){
            String key = itr.next();
            System.out.println(key + " = " + map.get(key));
        }


        Properties properties = System.getProperties();

        for(Iterator<String> itr = properties.stringPropertyNames().iterator(); itr.hasNext();){
            String key = itr.next();
            System.out.println(key + " = " + properties.get(key));
        }


    }

    public static String getOsName(){

        String osName = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US);

        LOGGER.info("osName ==> {}", osName);

        return osName;
    }




    private static io.netty.channel.ChannelFactory<? extends Channel> channelFactory = (ChannelFactory<Channel>) () -> {
        String osName = getOsName();
        if (osName.contains("linux")){
            return new EpollServerSocketChannel();
        } else if (osName.contains("freebsd")){
            return new KQueueServerSocketChannel();
        }
        return new NioServerSocketChannel();
    };



    public void start() {

        final EchoServerhandler serverhandler = new EchoServerhandler();

        final int bufferSize = 1024;

        // 1 创建 EventLoopGroup
        EventLoopGroup group = NettyHelper.getEventLoopGroup();

        try {
            // 2 创建 ServerBootstrap
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(group);

            // 3。 指定使用 NIO 传输 channel
            if (Epoll.isAvailable()) {
                bootstrap.channel(EpollServerSocketChannel.class)
                        //.option(ChannelOption.SO_BROADCAST, true)
                        .option(EpollChannelOption.SO_REUSEPORT, true);

            } else if (KQueue.isAvailable()) {
                bootstrap.channel(KQueueServerSocketChannel.class);
            } else {
                bootstrap.channel(NioServerSocketChannel.class)
                        .option(NioChannelOption.SO_REUSEADDR, true);
            }

            bootstrap.option(ChannelOption.SO_RCVBUF, 1024 * 1024 * bufferSize);


            /*
             * 添加一个 EchoServerHandler 到子 Channel 的
             * ChannelPipeline。
             * 当一个新的连接被接受时，一个新的字 Channel 会被创建
             */
            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline()
                            // EchoServerHandler 被标注为 @Shareable
                            // 所以我们总是使用同样的实例
                            .addLast(serverhandler);
                }
            });

            bootstrap.localAddress(new InetSocketAddress(port));

            /* 指定使用的端口 */
            if (Epoll.isAvailable()) {
                // linux系统下使用SO_REUSEPORT特性，使得多个线程绑定同一个端口
                int cpuNum = Runtime.getRuntime().availableProcessors();
                LOGGER.info("using epoll reuseport and cpu: {}", cpuNum);
                for (int i = 0; i < cpuNum; i++) {
                    ChannelFuture future = bootstrap.bind().sync();
                    if (!future.isSuccess()) {
                        throw new Exception("bootstrap bind fail port is " + port);
                    }
                    LOGGER.info("future sucess ==> {}", future.isSuccess());
                    future.channel().closeFuture().sync();
                }
            } else {

                // 异步地绑定服务器： 调用 sync() 方法
                // 阻塞等待，直到绑定完成。
                ChannelFuture f = bootstrap.bind().sync();

                // 7. 获取 Channel 的 closeFuture
                // 并且阻塞直到它完成
                f.channel().closeFuture().sync();
            }


        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            // 8 关闭 EventLoopGroup 释放所有资源。
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                LOGGER.error("", e);
                Thread.currentThread().interrupt();
            }
        }


    }
}
