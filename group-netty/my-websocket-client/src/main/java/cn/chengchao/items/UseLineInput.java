package cn.chengchao.items;

import cn.chengchao.helper.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UseLineInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(UseLineInput.class);

    public void execute(String clientUrl) {

        WebSocketClient client = new WebSocketClient();
        Session session = client.connect(clientUrl);
        // 300000
        long maxIdleTimeout = session.getMaxIdleTimeout();
        System.err.println("maxIdleTimeout = " + maxIdleTimeout );
        session.setMaxIdleTimeout(0L);

        Map<String, String> pathParameters = session.getPathParameters();
        pathParameters.forEach((key, value) -> System.out.println("key: "+ key + ",value: "+ value));

        Set<Session> openSessions = session.getOpenSessions();
        openSessions.forEach(System.out::println);

        final Session sess = session;
        new Thread(() -> {
            for(;;) {
                try {
                    System.out.println(LocalDateTime.now());
                    if (sess.isOpen()) {
                        sess.getAsyncRemote().sendPing(ByteBuffer.wrap("ping ...".getBytes()));
                    } else {
                        System.err.println(" thread exit ...");
                        break;
                    }
                    TimeUnit.SECONDS.sleep(60L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();


        String line = "";

        for(;;) {
            if (line.equals("exit") || line.equals("retry>=3")) break;
            int i = 0;
            int retry = 0;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
                do {
                    i += 1;

                    if (retry >= 3) {
                        line = "retry>=3";
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

                    session.getAsyncRemote().sendPing(ByteBuffer.wrap("ping ...".getBytes()));

                    //session.getBasicRemote().sendPong(ByteBuffer.wrap("pong ...".getBytes()));
                } while (true);
            } catch (IOException e) {
                LOGGER.error(":(", e);
            }
        }
        client.close(session, "Normal Exit");
        LOGGER.info("++++ done ++++");
    }
}
