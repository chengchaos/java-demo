package luxe.chaos.netty.keepalive.client.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadEchoHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MultiThreadEchoHandler.class);

    public static final int RECEIVING = 0;
    public static final int SENDING = 1;

    final SocketChannel channel;
    final SelectionKey key;

    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    int state = RECEIVING;

    static ExecutorService pool = Executors.newFixedThreadPool(4) ;

    public MultiThreadEchoHandler(Selector selector, SocketChannel channel) throws IOException {

        this.channel = channel;

        channel.configureBlocking(false);
        key = channel.register(selector, 0);
        key.attach(this);
        key.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        pool.execute(new AsyncTask());
    }

    class AsyncTask implements Runnable {

        @Override
        public void run() {
            MultiThreadEchoHandler.this.asyncRun();
        }
    }

    public synchronized void asyncRun() {
        try {
            if (state == SENDING) {
                // 写入
                channel.write(byteBuffer);
                // 然后，开始读
                byteBuffer.clear();
                key.interestOps(SelectionKey.OP_READ);
                state = RECEIVING;
            } else if (state == RECEIVING) {
                // 读
                int lenght = 0;
                while ((lenght = channel.read(byteBuffer)) > 0) {
                    if (logger.isInfoEnabled()) {
                        logger.info("read => {}", new String(byteBuffer.array(), 0, lenght));
                    }
                }
                byteBuffer.flip();
                key.interestOps(SelectionKey.OP_WRITE);
                state = SENDING;
            }
            // 处理结束了，这里不能关闭 select key， 需要重复使用

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
