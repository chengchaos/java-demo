package luxe.chaos.netty.keepalive.client.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 如题
 * </p>
 *
 * @author Cheng, Chao - 12/16/2020 5:05 PM <br />
 */

public class EchoClient {

    private static final Logger logger = LoggerFactory.getLogger(EchoClient.class);

    private static final StringDecoder stringDecoder = new StringDecoder();
    private static final StringEncoder stringEncoder = new StringEncoder();


    private EchoClientHandler echoHandler;

    public EchoClient(EchoClientHandler echoHandler) {
        this.echoHandler = echoHandler;
    }

    private NioEventLoopGroup workers;
    private int port;

    private int state = 0;


    public void connect(int port) {
        this.port = port;

        workers = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.group(workers);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("stringDecoder", stringDecoder);
                pipeline.addLast("stringEncoder", stringEncoder);
                pipeline.addLast("echoHandler", echoHandler);
            }
        });

        bootstrap.connect(new InetSocketAddress("47.114.98.28", port))
                .addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("连接成功");
                        this.state = 1;
                    } else {
                        logger.warn("连接失败");
                        this.state = 0;
                    }
                });
    }

    public void close() {
        this.workers.shutdownGracefully();
        this.state = 0;
    }

    public void reconnect() {
        this.close();
        this.connect(this.port);
    }
}
