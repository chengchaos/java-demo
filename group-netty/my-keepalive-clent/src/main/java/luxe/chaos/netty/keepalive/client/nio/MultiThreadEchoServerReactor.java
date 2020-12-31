package luxe.chaos.netty.keepalive.client.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadEchoServerReactor {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadEchoServerReactor.class);

    ServerSocketChannel serverSocketChannel;

    AtomicInteger next = new AtomicInteger(0);

    // 选择器集合
    Selector[] selectors = new Selector[2];
    // 子反应器
    SubReactor[] subReactors = null;

    MultiThreadEchoServerReactor() throws IOException {
        // 初始化多个选择器
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress("localhost", 7777);

        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);

        // 第一个选择器，负责监控新连接事件
        SelectionKey key = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
        // 绑定 Handler
        key.attach(new AcceptorHandler());

        // 第一个子反应器，一个子反应器负责一个选择器。
        SubReactor subReactor1 = new SubReactor(selectors[0]);
        // 第二个子反应器，
        SubReactor subReactor2 = new SubReactor(selectors[1]);

        subReactors = new SubReactor[]{subReactor1, subReactor2};
    }

    private void startServer() {
        // 一个子反应器一个线程。
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    static class SubReactor implements Runnable {

        final Selector selector;

        public SubReactor(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    for (SelectionKey key : keySet) {
                        dispatch(key);
                    }
                    keySet.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void dispatch(SelectionKey key) {
            Runnable handler = (Runnable) key.attachment();
            if (handler != null) {
                handler.run();
            }
        }
    }

    class AcceptorHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocketChannel.accept();
                if (channel != null) {
                    new MultiThreadEchoHandler(selectors[next.get()], channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (next.incrementAndGet() == selectors.length) {
                next.set(0);
            }
        }
    }
}
