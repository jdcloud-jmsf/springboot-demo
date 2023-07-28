package com.jdcloud.jmsf.springbootdemo;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
       String longConnectionEnable =  System.getenv("LONG_CONNECTION_ENABLE");
        if (longConnectionEnable != null) {
            return builder.requestFactory(this::httpComponentsClientHttpRequestFactory).build();
        }
        return new RestTemplate();
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient(poolingHttpClientConnectionManager(), requestConfig()));
        return factory;
    }

    @Bean
    public RequestConfig requestConfig() {

        RequestConfig result = RequestConfig.custom()
                .setConnectionRequestTimeout(0)
                .setConnectTimeout(2000)
                .setSocketTimeout(2000)
                .build();

        return result;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager, RequestConfig requestConfig) {

        CloseableHttpClient result = HttpClientBuilder
                .create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionTimeToLive(30, TimeUnit.SECONDS)
                .build();

        return result;
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setValidateAfterInactivity(2000);
        return connectionManager;
    }
}
