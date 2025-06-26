package com.pokeapi.service.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SpriteResponse
 *
 * @author Jean
 */
public record Sprite(
        @JsonProperty("front_default") String frontDefault
) {

}
