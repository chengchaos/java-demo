package cn.chengchao.items;

import cn.chengchao.helper.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;

public class SendCommand1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendCommand1.class);

    public void execute(String clientUrl) {

        WebSocketClient client = new WebSocketClient();
        Session session = client.connect(clientUrl);

//        session.getBasicRemote().sendText(line);

        client.close(session, "Normal Exit");
        LOGGER.info("++++ done ++++");
    }
}
