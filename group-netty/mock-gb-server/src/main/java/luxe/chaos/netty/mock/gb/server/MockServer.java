package luxe.chaos.netty.mock.gb.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import luxe.chaos.netty.mock.gb.handlers.Handlers;
import luxe.chaos.netty.mock.gb.handlers.PacketDecoder;
import luxe.chaos.netty.mock.gb.handlers.PacketEncoder;
import luxe.chaos.netty.mock.gb.handlers.ResponseHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

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
@Component(value = "mockServer")
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

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        Pair<EventLoopGroup, EventLoopGroup> loopGroup = MockServers.allocateEventLoopGroup();

        this.boss = loopGroup.getLeft();
        this.works = loopGroup.getRight();

        serverBootstrap.group(boss, works);

        MockServers.allocateChannel(serverBootstrap);

        serverBootstrap.localAddress(new InetSocketAddress(port));
        serverBootstrap.childHandler(this.newChannelInitializer());
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        serverBootstrap.childOption(ChannelOption.SO_LINGER, 0);
        serverBootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 60 * 1000);

        MockServers.bind(serverBootstrap, port);

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
