package cn.chengchao.items;

import cn.chengchao.helper.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class UseLineInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(UseLineInput.class);

    public void execute(String clientUrl) {

        WebSocketClient client = new WebSocketClient();
        Session session = client.connect(clientUrl);

        String line;

        int i = 0;
        int retry = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                i += 1;

                if (retry >= 3) {
                    break;
                }
                if (session == null || !session.isOpen()) {
                    session = client.connect(clientUrl);
                    retry += 1;
                    continue;
                }

                line = br.readLine();
                if (line.equals("exit")) {
                    break;
                }

                if (i % 2 == 0) {
                    session.getBasicRemote().sendText(line);
                } else {
                    session.getAsyncRemote().sendText(line);
                }

                session.getAsyncRemote().sendPing(ByteBuffer.wrap("chengchao".getBytes()));

            } while (true);


        } catch (IOException e) {
            LOGGER.error(":(", e);
        }

        client.close(session, "Normal Exit");
        LOGGER.info("++++ done ++++");
    }
}
