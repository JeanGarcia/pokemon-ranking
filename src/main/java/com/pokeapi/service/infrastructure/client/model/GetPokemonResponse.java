package com.pokeapi.service.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetPokemonResponse
 *
 * @author Jean
 */
public record GetPokemonResponse(
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("height") int height,
        @JsonProperty("weight") int weight,
        @JsonProperty("base_experience") int baseExperience,
        @JsonProperty("sprites") Sprite sprites
) {

}
