package com.pokeapi.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

/**
 * SwaggerConfiguration
 * All the configuration for swagger and OpenAPI doc.
 *
 * @author Jean
 */
public class SwaggerConfiguration {

    @Bean
    public OpenAPI pokeapiServiceOpenAPI() {
        return new OpenAPI() 
            .info(new Info().title("Pokemon Ranking API")
            .description("A Pokemon ranking API that allows you to rank Pokémon based on various stats such as base experience, weight, and height.")
            .version("0.0.1"));
    }
    
}
