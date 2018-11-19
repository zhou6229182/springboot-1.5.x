package com.ytjr.api.utils;

import com.ytjr.common.enums.ResponseEnums;
import com.ytjr.common.exception.FinancialServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class HttpClientUtils<T, V> {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private RestTemplate restTemplate;

    @Autowired
    public HttpClientUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 发送 post 请求
     * 请求参数为非实体类时，T类型须为 LinkedMultiValueMap 类型
     */
    public V post(T t, String url, Class<V> vClass) throws FinancialServiceException {
        RequestEntity<T> requestEntity;
        ResponseEntity<V> responseEntity;
        try {
            requestEntity = RequestEntity
                    .post(new URI(url))
                    .body(t);
            responseEntity = this.restTemplate.exchange(requestEntity, vClass);
        } catch (URISyntaxException e) {
            logger.error("uri格式错误 uri = {}", url, e);
            throw new FinancialServiceException(ResponseEnums.SYSTEM_ERROR, e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("网络请求错误 url = {}, statusCode = {}, statusText = {}", url, e.getStatusCode().value(), e.getStatusText(), e);
            throw new FinancialServiceException(ResponseEnums.SYSTEM_ERROR, e);
        } catch (UnknownHttpStatusCodeException e) {
            logger.error("网络请求错误 url = {}, statusCode = {}, statusText = {}", url, e.getRawStatusCode(), e.getStatusText(), e);
            throw new FinancialServiceException(ResponseEnums.SYSTEM_ERROR, e);
        }
        return responseEntity.getBody();
    }

    public V get(String url, Class<V> vClass) throws FinancialServiceException {
        RequestEntity requestEntity;
        ResponseEntity<V> responseEntity;
        try {
            requestEntity = RequestEntity
                    .get(new URI(url)).build();
            responseEntity = this.restTemplate.exchange(requestEntity, vClass);
        } catch (URISyntaxException e) {
            logger.error("uri格式错误 uri = {}", url, e);
            throw new FinancialServiceException(ResponseEnums.SYSTEM_ERROR, e);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("网络请求错误 url = {}, statusCode = {}, statusText = {}", url, e.getStatusCode().value(), e.getStatusText(), e);
            throw new FinancialServiceException(ResponseEnums.SYSTEM_ERROR, e);
        } catch (UnknownHttpStatusCodeException e) {
            logger.error("网络请求错误 url = {}, statusCode = {}, statusText = {}", url, e.getRawStatusCode(), e.getStatusText(), e);
            throw new FinancialServiceException(ResponseEnums.SYSTEM_ERROR, e);
        }
        return responseEntity.getBody();
    }

}
