package luxe.chaos.netty.mock.gb.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import luxe.chaos.netty.mock.gb.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/16/2020 3:16 PM <br />
 * @see [相关类方法]
 * @since [产品模块版本]
 */
@Component
public class MockServer {

    private static final Logger logger = LoggerFactory.getLogger(MockServer.class);

    private int state = 0; // 0 停止； 1 运行

    private static final StringDecoder stringDecoder = new StringDecoder();
    private static final StringEncoder stringEncoder = new StringEncoder();


    @Autowired
    private ResponseHandler responseHandler;


    private EventLoopGroup boss;
    private EventLoopGroup works;

    /**
     * @param port Listen port
     */
    public void start(int port) {

        ServerBootstrap sb = new ServerBootstrap();
        int bufferSize = 1024;

        int availableProcessors = Runtime.getRuntime().availableProcessors();

        this.boss = new NioEventLoopGroup(availableProcessors);
        this.works = new NioEventLoopGroup();

        sb.group(boss, works);

        sb.channel(NioServerSocketChannel.class);
        sb.localAddress(new InetSocketAddress(port));


        sb.option(ChannelOption.SO_REUSEADDR, true);
        sb.option(ChannelOption.SO_RCVBUF, 1024 * bufferSize);
        sb.option(ChannelOption.SO_BACKLOG, 1024);

        sb.childHandler(this.newChannelInitializer());

        sb.childOption(ChannelOption.SO_KEEPALIVE, true);
        sb.childOption(ChannelOption.TCP_NODELAY, true);
        sb.childOption(ChannelOption.SO_LINGER, 0);

        sb.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 1000);


        ChannelFuture channelFuture = sb.bind(port)
                .addListener(future -> logger.info("Mock Server 启动，监听端口 : {}", port));

        channelFuture.channel()
                .closeFuture()
                .addListener((ChannelFutureListener) future -> logger.info("{} 链路关闭。", future.channel()));

        this.state = 1;
    }

    /**
     *
     */
    public void stop() {
        this.works.shutdownGracefully();
        this.boss.shutdownGracefully();
        this.boss = null;
        this.works = null;
        this.state = 0;
    }

    private ChannelInitializer<SocketChannel> newChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();

                pipeline.addLast("loggingHandler", new LoggingHandler(LogLevel.INFO));
                pipeline.addLast("LengthFieldBasedFrameDecoder", Handlers.newLengthFieldBasedFrameDecoder());

                /* 添加解码器*/
                pipeline.addLast("encoder", new PacketEncoder());
                pipeline.addLast("decoder", new PacketDecoder());

                pipeline.addLast("responseHandler", responseHandler);


            }
        };
    }

    public boolean isRun() {
        return this.state == 1;
    }
}
