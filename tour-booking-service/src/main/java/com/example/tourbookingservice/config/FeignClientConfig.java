package com.example.tourbookingservice.config;


import feign.Client;
import feign.RequestInterceptor;
import feign.httpclient.ApacheHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientConfig {

    @Bean
    public Client feignClient() {
        return new ApacheHttpClient(HttpClients.createDefault());
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String authorization = RequestContextHolder.getRequestAttributes() != null ?
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization") : null;
            if (authorization != null) {
                requestTemplate.header("Authorization", authorization);
            }
        };
    }
}
