package com.jmdecerio.spring_ai_mcp_client.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class ApacheHttpClientConfig {

    @Bean
    RestClientCustomizer restClientCustomizer() {
        return builder -> {
            var requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(Timeout.ofSeconds(3))
                    .setResponseTimeout(Timeout.ofSeconds(15))
                    .build();
            var httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .build();

            builder.requestFactory(
                    new HttpComponentsClientHttpRequestFactory(httpClient)
            );
        };
    }
}