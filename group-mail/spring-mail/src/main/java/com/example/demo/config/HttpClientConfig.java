package com.example.demo.config;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 3/6/2021 6:58 PM <br />
 * @see [相关类]
 * @since 1.0
 */
@Configuration
public class HttpClientConfig {


    @Value("${http_max_total}")
    private int maxTotal = 800;

    @Value("${http_default_max_perRoute}")
    private int defaultMaxPerRoute = 80;

    @Value("${http_validate_after_inactivity}")
    private int validateAfterInactivity = 1000;

    @Value("${http_connection_request_timeout}")
    private int connectionRequestTimeout = 5000;

    @Value("${http_connection_timeout}")
    private int connectTimeout = 10000;

    @Value("${http_socket_timeout}")
    private int socketTimeout = 20000;

    @Value("${waitTime}")
    private int waitTime = 30000;

    @Value("${idleConTime}")
    private int idleConTime = 3;

    @Value("${retryCount}")
    private int retryCount = 3;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(maxTotal);
        manager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        manager.setValidateAfterInactivity(validateAfterInactivity);

        return manager;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient(PoolingHttpClientConnectionManager poolManager) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(poolManager);
        httpClientBuilder.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
                HeaderElementIterator iter = new BasicHeaderElementIterator(headerIterator);
                while (iter.hasNext()) {
                    HeaderElement headerElement = iter.nextElement();
                    String param = headerElement.getName();
                    String value = headerElement.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value, 10) * 1000;
                    }
                }
                return 30 * 1000L;
            }
        });
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, false));
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
    }

    @Bean
    public IdleConnectionEvictor idleConnectionEvictor(PoolingHttpClientConnectionManager poolManager) {

        return new IdleConnectionEvictor(poolManager, waitTime, TimeUnit.MINUTES);
    }
}
