package com.pokeapi.service.infrastructure.client;

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
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .codecs(configure -> configure.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }
}