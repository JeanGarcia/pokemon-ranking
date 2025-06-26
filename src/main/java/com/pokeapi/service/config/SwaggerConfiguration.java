package com.pokeapi.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * SwaggerConfiguration
 * All the configuration for swagger and OpenAPI doc.
 *
 * @author Jean
 */
public class SwaggerConfiguration {

    @Value("${application.title}")
    private String appTitle;

    @Value("${application.description}")
    private String appDescription;

    @Value("${application.version}")
    private String appVersion;

    @Bean
    public OpenAPI pokeapiServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(appTitle)
                        .description(appDescription)
                        .version(appVersion));
    }

}
