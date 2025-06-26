package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PokemonDto
 *
 * @author Jean
 */
public record PokemonDto(
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("base_experience") int baseExperience,
        @JsonProperty("height") int height,
        @JsonProperty("weight") int weight,
        @JsonProperty("sprite_url") String spriteUrl
) {

}
