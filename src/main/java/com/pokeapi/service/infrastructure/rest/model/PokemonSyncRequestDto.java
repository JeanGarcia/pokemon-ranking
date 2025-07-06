package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * PokemonSyncRequestDto
 *
 * @author Jean
 */
public record PokemonSyncRequestDto(
        @NotBlank
        @JsonProperty("pokemon_id")
        String pokemonId
) {
}
