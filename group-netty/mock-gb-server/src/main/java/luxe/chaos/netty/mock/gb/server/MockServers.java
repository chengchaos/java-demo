package luxe.chaos.netty.mock.gb.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.unix.UnixChannelOption;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class MockServers {


    private static final Logger logger = LoggerFactory.getLogger(MockServers.class);

    private static final int BUFFER_SIZE = 1024;
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    static {
        logger.info("Epoll.isAvailable() -=> {}", Epoll.isAvailable());
        logger.info("KQueue.isAvailable() -=> {}", KQueue.isAvailable());
    }

    private MockServers() {
        super();

    }

    public static Pair<EventLoopGroup, EventLoopGroup> allocateEventLoopGroup() {

        if (Epoll.isAvailable()) {
            return Pair.of(new EpollEventLoopGroup(AVAILABLE_PROCESSORS), new EpollEventLoopGroup());
        } else if (KQueue.isAvailable()) {
            return Pair.of(new KQueueEventLoopGroup(AVAILABLE_PROCESSORS), new KQueueEventLoopGroup());
        } else {
            return Pair.of(new NioEventLoopGroup(AVAILABLE_PROCESSORS), new NioEventLoopGroup());
        }
    }

    public static ServerBootstrap allocateChannel(ServerBootstrap sb) {


        sb.option(ChannelOption.SO_RCVBUF, 1024 * BUFFER_SIZE);
        sb.option(ChannelOption.SO_REUSEADDR, true);
        sb.option(ChannelOption.SO_BACKLOG, 1024);
        sb.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 60 * 1000);

        if (Epoll.isAvailable()) {
            sb.channel(EpollServerSocketChannel.class);
            sb.option(UnixChannelOption.SO_REUSEPORT, true);
        } else if (KQueue.isAvailable()) {
            sb.channel(KQueueServerSocketChannel.class);
            sb.option(UnixChannelOption.SO_REUSEPORT, true);
        } else {
            sb.channel(NioServerSocketChannel.class);

        }
        return sb;
    }


    public static void bind(ServerBootstrap sb, int port) {

        if (Epoll.isAvailable() || KQueue.isAvailable()) {
            IntStream.rangeClosed(1, AVAILABLE_PROCESSORS)
                    .forEach(i -> {

                        ChannelFuture channelFuture = sb.bind(port)
                                .addListener(future -> {

                                    if (future.isSuccess()) {
                                        logger.info("Mock Server 端口绑定成功 {} ==> {}", i, port);
                                    } else {
                                        logger.info("Mock Server 端口绑定失败 {} ==> {}", i, port);
                                    }
                                });

                        channelFuture.channel()
                                .closeFuture()
                                .addListener((ChannelFutureListener) future -> logger.info("{} 链路关闭。", future.channel()));

                    });
        } else {
            ChannelFuture channelFuture = sb.bind(port)
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            logger.info("Mock Server 端口绑定成功 ==> {}", port);
                        } else {
                            logger.info("Mock Server 端口绑定失败 ==> {}", port);
                        }
                    });

            channelFuture.channel()
                    .closeFuture()
                    .addListener((ChannelFutureListener) future -> logger.info("{} 链路关闭。", future.channel()));

        }


    }
}
