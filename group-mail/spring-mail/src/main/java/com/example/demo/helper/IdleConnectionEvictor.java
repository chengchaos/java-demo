package com.example.demo.helper;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 *     定期清理无效的 HTTP 连接
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/6/2021 9:49 PM <br />
 * @see [相关类]
 * @since 1.0
 */
public class IdleConnectionEvictor extends Thread {

    private final HttpClientConnectionManager manager;

    private int waitTime;

    private int idleConTime;

    private volatile boolean shutdown = false;

    public IdleConnectionEvictor(HttpClientConnectionManager manager, int waitTime, int idleConTime) {
        this.manager = manager;
        this.waitTime = waitTime;
        this.idleConTime = idleConTime;
        this.start();
    }

    @Override
    public void run() {
        try {
            if (!shutdown) {
                synchronized (this) {
                    wait(waitTime);
                    manager.closeIdleConnections(idleConTime, TimeUnit.SECONDS);
                    // 关闭失效的连接
                    manager.closeExpiredConnections();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
