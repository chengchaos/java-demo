package luxe.chaos.netty.mock.gb.niodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title said.
 * </p>
 *
 * @author Cheng, Chao - 12/30/2020 10:31 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class NIODemoServer {

    private static final Logger logger = LoggerFactory.getLogger(NIODemoServer.class);

    private final int port = 60001;
    private Selector serverSelector;
    private Selector workerSelector;

    private Runnable serverRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(port));
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(serverSelector, SelectionKey.OP_ACCEPT);

                logger.info("server selector select size => ");

// !Thread.interrupted() && serverSelector.select(1L) > 0
                while (!Thread.interrupted()) {
                    int selectCount = serverSelector.select(1L);


                    if (selectCount > 0) {
                        Set<SelectionKey> keySet = serverSelector.selectedKeys();
                        logger.info("server selector selectCount size => {}", keySet.size());

                        Iterator<SelectionKey> iterator = keySet.iterator();

                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            if (key.isAcceptable()) {
                                logger.info("key is acceptable.");

                                try {
                                    SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                                    socketChannel.configureBlocking(false);
                                    socketChannel.register(workerSelector, SelectionKey.OP_READ);
                                } finally {
                                    iterator.remove();
                                }
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    private Runnable workerRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                // !Thread.interrupted() && workerSelector.select(1L) > 0
                while (true) {


                    if (workerSelector.select(1L) > 0) {

                        Set<SelectionKey> keySet = workerSelector.selectedKeys();
                        Iterator<SelectionKey> iterator = keySet.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            if (key.isReadable()) {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    socketChannel.read(buffer);
                                    buffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(buffer).toString());

                                } finally {
                                    iterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void start() {

        logger.info("start ...");

        try {
            this.serverSelector = Selector.open();
            this.workerSelector = Selector.open();
            new Thread(this.serverRunnable).start();
            new Thread(this.workerRunnable).start();
            TimeUnit.MINUTES.sleep(1L);
            System.out.println("........");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new NIODemoServer().start();
    }
}
