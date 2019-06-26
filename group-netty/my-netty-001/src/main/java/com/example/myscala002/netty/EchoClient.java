package com.example.myscala002.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题。
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/4/10 0010 下午 3:48 <br />
 * @see 【相关类方法】
 * @since 1.1.0
 */
public class EchoClient {

    private final String host;

    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private EventLoopGroup eventLoopGroup;

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public void start() throws Exception {

        eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture connect = bootstrap.connect();
            System.out.println("connect ==> "+ connect);
            //ChannelFuture future = bootstrap.connect().sync();
            //future.channel().closeFuture().sync();

        } finally {
            //eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {


        EchoClient client = new EchoClient("localhost", 8384);

        for (int i = 0; i < 3; i++) {

            client.start();

            TimeUnit.SECONDS.sleep(5L);
            System.err.println("-- end -- line 1:"+ i);

            client.getEventLoopGroup().shutdownGracefully();

            System.err.println("-- end -- line 2: "+ i);
        }
    }
}
