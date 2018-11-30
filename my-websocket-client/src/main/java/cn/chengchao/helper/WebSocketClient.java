package cn.chengchao.helper;

import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * https://www.cnblogs.com/yiwangzhibujian/p/5858544.html
 */
@ClientEndpoint
public class WebSocketClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketClient.class);

    private final CountDownLatch closeLatch;

    private final WebSocketContainer container;

    public WebSocketClient() {
        closeLatch =  new CountDownLatch(1);
        container = ContainerProvider.getWebSocketContainer();
        awaitClose(1, TimeUnit.SECONDS);
    }

    public boolean awaitClose(int duration, TimeUnit unit) {
        try {
            return this.closeLatch.await(duration,unit);
        } catch (InterruptedException e) {
            LOGGER.error(":(", e);
            Thread.currentThread().interrupt();
        }
        return false;
    }


    @OnOpen
    public void onOpen(Session session) {

        LOGGER.info("open ... session = {}", session);
    }

    @OnMessage
    public void onMessage(String message) {

        LOGGER.info("<<< : {}", message);
    }

    @OnClose
    public void onClose(Session session) {

        LOGGER.info("close ... session = {}", session);

        this.closeLatch.countDown(); // 触发位置
    }


    public Session connect(String clientUrl) {


        URI uri = URI.create(clientUrl);
        try {
            return  container.connectToServer(WebSocketClient.class, uri);
        } catch ( DeploymentException | IOException e) {
            LOGGER.error(":(", e);
        }

        return null;
    }

    /**
     *
     * @param session Session
     */
    public void close(Session session, String reasonPhrase) {
        if (StringUtil.isBlank(reasonPhrase)) {
            reasonPhrase = "bye ...";
        }
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, reasonPhrase ));
        } catch (IOException e) {
            LOGGER.error(":(", e);
        }

    }


}
