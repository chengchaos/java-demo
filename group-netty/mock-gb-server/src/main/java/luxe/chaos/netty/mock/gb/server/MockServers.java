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
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockServers {


    private static final Logger logger = LoggerFactory.getLogger(MockServers.class);

    private static final int bufferSize = 1024;
    private static final int availableProcessors = Runtime.getRuntime().availableProcessors();

    private MockServers() {
        super();
    }

    public static Pair<EventLoopGroup, EventLoopGroup> allocateEventLoopGroup() {

        if (Epoll.isAvailable()) {
            return Pair.of(new EpollEventLoopGroup(availableProcessors), new EpollEventLoopGroup());
        } else if (KQueue.isAvailable()) {
            return Pair.of(new KQueueEventLoopGroup(availableProcessors), new KQueueEventLoopGroup());
        } else {
            return Pair.of(new NioEventLoopGroup(availableProcessors), new NioEventLoopGroup());
        }
    }

    public static ServerBootstrap allocateChannel(ServerBootstrap sb) {


        sb.option(ChannelOption.SO_RCVBUF, 1024 * bufferSize);
        sb.option(ChannelOption.SO_REUSEADDR, true);
        sb.option(ChannelOption.SO_BACKLOG, 1024);
        sb.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30 * 60 * 1000);

        if (Epoll.isAvailable()) {
            sb.channel(EpollServerSocketChannel.class);
        } else if (KQueue.isAvailable()) {
            sb.channel(KQueueServerSocketChannel.class);
        } else {
            sb.channel(NioServerSocketChannel.class);

        }
        return sb;
    }


    public static void bind(ServerBootstrap sb, int port) {

        if (Epoll.isAvailable() || KQueue.isAvailable()) {
            final int[] ib = new int[0];
            for (int i = 0; i < availableProcessors; i++) {
                ib[0] = i;
                ChannelFuture channelFuture = sb.bind(port)
                        .addListener(future -> {
                            final int cpu = ib[0];
                            if (future.isSuccess()) {
                                logger.info("Mock Server 端口绑定成功 {} ==> {}", cpu, port);
                            } else {
                                logger.info("Mock Server 端口绑定失败 {} ==> {}", cpu, port);
                            }
                        });

                channelFuture.channel()
                        .closeFuture()
                        .addListener((ChannelFutureListener) future -> logger.info("{} 链路关闭。", future.channel()));
            }

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
