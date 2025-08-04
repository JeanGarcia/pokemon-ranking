package com.pokeapi.service.infrastructure.client.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ResultItem
 *
 * @author Jean
 */
public record ResultItem(
        @JsonProperty("name") String name,
        @JsonProperty("url") String url
) {

}
