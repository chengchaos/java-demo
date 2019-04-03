package com.example.myjsp.conf;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.commons.httpclient.DefaultApacheHttpClientConnectionManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * 功能的详细描述
 * </p>
 *
 * @author chengchaos[as]Administrator - 2019/3/28 0028 下午 9:08 <br />
 * @since 1.1.0
 */
@Configuration
public class RestTemplateConfig {

    @Bean(name="loadBalanced")
    @LoadBalanced
    public RestTemplate loadBalanced() {

        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean(name="normal")
    @Primary  // 在同样的 DataSource 中，首先使用被标注的 DataSource .
    public RestTemplate normal() {

        return new RestTemplate(clientHttpRequestFactory());
    }


    private HttpClientConnectionManager httpClientConnectionManager() {

        DefaultApacheHttpClientConnectionManagerFactory
                defaultApacheHttpClientConnectionManagerFactory =
                new DefaultApacheHttpClientConnectionManagerFactory();

        return defaultApacheHttpClientConnectionManagerFactory
                .newConnectionManager(true
                        , 500
                        , 50);
    }

    private HttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager());
        return httpClientBuilder.build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory
                clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
        clientHttpRequestFactory.setConnectTimeout(30000);
        clientHttpRequestFactory.setReadTimeout(30000);
        return clientHttpRequestFactory;
    }
}
