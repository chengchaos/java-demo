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
import org.apache.commons.lang3.tuple.Pair;
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

        Pair<EventLoopGroup, EventLoopGroup> elps = MockServers.allocateEventLoopGroup();

        this.boss = elps.getLeft();
        this.works = elps.getRight();

        sb.group(boss, works);

        MockServers.allocateChannel(sb);

        sb.localAddress(new InetSocketAddress(port));
        sb.childHandler(this.newChannelInitializer());
        sb.childOption(ChannelOption.SO_KEEPALIVE, true);
        sb.childOption(ChannelOption.TCP_NODELAY, true);
        sb.childOption(ChannelOption.SO_LINGER, 0);
        sb.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 60 * 1000);

        MockServers.bind(sb, port);

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
