package com.ndangducbn.ducterrybase.utils;

import com.ndangducbn.ducterrybase.common.constant.define.ResponseObject;
import com.ndangducbn.ducterrybase.common.constant.define.ResponseStatus;
import com.ndangducbn.ducterrybase.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;

@Slf4j
public class RestFactory {


    public static HttpHeaders createHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));

        return httpHeaders;
    }

    public static <T> T postForEntityBasicToken(String url, Object body, Class<T> clazz, String token) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

            HttpEntity<Object> request = new HttpEntity<>(body, httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<T> response = restTemplate.postForEntity(url, request, clazz);

            if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null) {
                return response.getBody();
            }
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        } catch (RestClientException e) {
            log.error("RestClientException => {}", JSONFactory.toString(e.getMessage()));

            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        }
    }

    public static <T> ResponseObject<T> postForEntityBasicToken(String url,
                                                                Object body,
                                                                ParameterizedTypeReference<ResponseObject<T>> responseType,
                                                                String token) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> request = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<ResponseObject<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    responseType
            );
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null) {
                return response.getBody();
            }
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        } catch (RestClientException e) {
            log.error("RestClientException => {}", JSONFactory.toString(e.getMessage()));

            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        }
    }

    public static <T> T postForEntity(String url, Object body, Class<T> clazz, String token) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> request = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<T> response = restTemplate.postForEntity(url, request, clazz);
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null) {
                return response.getBody();
            }
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        } catch (RestClientException e) {
            log.error("RestClientException => {}", JSONFactory.toString(e.getMessage()));
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        }
    }

    public static <T> ResponseObject<T> postForEntity(String url,
                                                      Object body,
                                                      ParameterizedTypeReference<ResponseObject<T>> responseType,
                                                      String token) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> request = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<ResponseObject<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    responseType
            );
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null) {
                return response.getBody();
            }
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        } catch (RestClientException e) {
            log.error("RestClientException => {}", JSONFactory.toString(e.getMessage()));
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        }
    }

    public static <T> ResponseObject<T> postForEntity(String url,
                                                      Object body,
                                                      ParameterizedTypeReference<ResponseObject<T>> responseType,
                                                      String token,
                                                      Duration timeout) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            RestTemplate restTemplate = (new RestTemplateBuilder()).setReadTimeout(timeout).build();
            ResponseEntity<ResponseObject<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(body, httpHeaders),
                    responseType
            );
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null) {
                return response.getBody();
            }
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        } catch (RestClientException e) {
            log.error("RestClientException => {}", JSONFactory.toString(e.getMessage()));
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        }
    }

    public static <T> ResponseObject<T> getForEntity(String url,
                                                     ParameterizedTypeReference<ResponseObject<T>> responseType,
                                                     String token) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders();
            httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Object> request = new HttpEntity<>(httpHeaders);
            ResponseEntity<ResponseObject<T>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    responseType
            );
            if (HttpStatus.OK.equals(response.getStatusCode()) && response.getBody() != null) {
                return response.getBody();
            }
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        } catch (RestClientException e) {
            log.error("RestClientException => {}", JSONFactory.toString(e.getMessage()));
            throw new ApiException( ResponseStatus.UNHANDLED_ERROR);
        }
    }
}
