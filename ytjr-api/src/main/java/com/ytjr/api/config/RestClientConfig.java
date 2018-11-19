package com.ytjr.api.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class RestClientConfig {

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(1000);// 最大连接数
        connectionManager.setDefaultMaxPerRoute(1000);// 同路由并发数

        return HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        clientHttpRequestFactory.setConnectTimeout(5000);// 连接超时时间
        clientHttpRequestFactory.setReadTimeout(5000);// 数据读取超时时间
        // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
        clientHttpRequestFactory.setConnectionRequestTimeout(200);
        // 缓冲请求数据，默认值是true。通过POST或者PUT大量发送数据时，建议将此属性更改为false，以免耗尽内存。
        clientHttpRequestFactory.setBufferRequestBody(false);
        return clientHttpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        // 使用 utf-8 编码集的 conver 替换默认的 conver（默认的 string conver 的编码集为"ISO-8859-1"）
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        messageConverters.removeIf(converter -> converter instanceof StringHttpMessageConverter);
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }
}
