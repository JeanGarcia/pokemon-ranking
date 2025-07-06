package com.pokeapi.service.infrastructure.client.pokeapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * PokeapiClientConfiguration
 *
 * @author Jean
 */
@Configuration
public class PokeapiClientConfiguration {

    @Value("${pokeapi.client.base-url}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .codecs(configure -> configure.defaultCodecs().maxInMemorySize(1048576))
                .build();
    }
}