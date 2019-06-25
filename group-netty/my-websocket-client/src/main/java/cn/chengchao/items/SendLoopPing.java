package cn.chengchao.items;

import cn.chengchao.helper.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class SendLoopPing {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendLoopPing.class);

    public void execute(String clientUrl) {

        WebSocketClient client = new WebSocketClient();
        Session session = client.connect(clientUrl);

//        session.getBasicRemote().sendText(line)

        for (int i = 0; i < 100; i++) {
            try {
                String hello = "hello";
                LOGGER.info("send ping : {}", hello);
                session.getBasicRemote().sendPing(ByteBuffer.wrap(hello.getBytes()));
                TimeUnit.SECONDS.sleep(20L);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        client.close(session, "Normal Exit");
        LOGGER.info("++++ done ++++");
    }
}
