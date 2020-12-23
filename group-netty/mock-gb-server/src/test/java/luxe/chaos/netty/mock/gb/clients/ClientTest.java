package luxe.chaos.netty.mock.gb.clients;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import luxe.chaos.netty.mock.gb.handlers.PacketDecoder;
import luxe.chaos.netty.mock.gb.handlers.PacketEncoder;
import luxe.chaos.netty.mock.gb.handlers.RequestHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ClientTest {


    private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);


    static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;
    static final int LENGTH_FIELD_OFFSET = 22;
    static final int LENGTH_FIELD_LENGTH = 2;
    static final int LENGTH_ADJUSTMENT = 1;
    static final int INITIAL_BYTES_TO_STRIP = 0;

    public static LengthFieldBasedFrameDecoder newLengthFieldBasedFrameDecoder() {
        return new LengthFieldBasedFrameDecoder(
                MAX_FRAME_LENGTH,
                LENGTH_FIELD_OFFSET,
                LENGTH_FIELD_LENGTH,
                LENGTH_ADJUSTMENT,
                INITIAL_BYTES_TO_STRIP) {
        };
    }

    /**
     *
     */
    @Test
    public void platformLoginTest() {

        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup elg = new NioEventLoopGroup();
        bootstrap.group(elg);
        bootstrap.channel(NioSocketChannel.class);


        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("loggingHandler", new LoggingHandler(LogLevel.INFO));
                pipeline.addLast("LengthFieldBasedFrameDecoder", newLengthFieldBasedFrameDecoder());

                /* 添加解码器*/
                pipeline.addLast("decoder", new PacketDecoder());
                pipeline.addLast("encoder", new PacketEncoder());
                pipeline.addLast("requestHandler", new RequestHandler());
            }
        });


        String hostname = "32960test.evsmc.org";
        int port = 19007;
        hostname = "123.127.164.36";
        port = 19006;

        bootstrap.connect(new InetSocketAddress(hostname, port))

                .addListener(future -> {
                    if (future.isSuccess()) {
                        logger.info("连接成功");
                    } else {
                        logger.warn("连接失败");
                    }
                });

        try {
            TimeUnit.SECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        elg.shutdownGracefully();
    }
}
