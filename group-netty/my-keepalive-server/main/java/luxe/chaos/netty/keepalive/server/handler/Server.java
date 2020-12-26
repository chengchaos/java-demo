package luxe.chaos.netty.keepalive.server.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
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
@Component
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private int state = 0; // 0 停止； 1 运行




    private static final StringDecoder stringDecoder = new StringDecoder();
    private static final StringEncoder stringEncoder = new StringEncoder();


    @Autowired
    private EchoHandler echoHandler;


    private EventLoopGroup boss ;
    private EventLoopGroup works;

    /**
     *
     * @param port Listen port
     */
    public void start(int port) {
        ServerBootstrap sb = new ServerBootstrap();

        this.boss = new NioEventLoopGroup();
        this.works = new NioEventLoopGroup();

        sb.group(boss, works);

        sb.channel(NioServerSocketChannel.class);
        sb.localAddress(new InetSocketAddress(port));

        sb.childHandler(this.newChannelInitializer());

        sb.bind(port)
                .addListener(future -> logger.info("server started !"));
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
                pipeline.addLast("stringDecoder", stringDecoder);
                pipeline.addLast("stringEncoder", stringEncoder);
                pipeline.addLast("echoHandler", echoHandler);
            }
        };
    }

    public boolean isRun() {
        return this.state == 1;
    }
}
