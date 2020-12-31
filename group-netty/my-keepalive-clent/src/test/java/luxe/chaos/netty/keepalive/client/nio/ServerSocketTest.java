package luxe.chaos.netty.keepalive.client.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketTest {

    public void start() throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        // 非阻塞情况下，与服务的连接可能还没有建立，connect 方法就返回了。
        // 因此需要不断自旋检查是否连接到了主机。
        while (!socketChannel.finishConnect()) {
            // 不断自选/等待/或者做其他事。
        }

    }
    @Test
    public void testStart() {



    }
}
