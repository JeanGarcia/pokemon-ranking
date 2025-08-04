package com.pokeapi.service.infrastructure.client.pokeapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * GetPokemonListResponse
 *
 * @author Jean
 */
public record GetAllPokemonResponse(
        @JsonProperty("count") int count,
        @JsonProperty("next") String next,
        @JsonProperty("previous") String previous,
        @JsonProperty("results") List<ResultItem> results
) {

}
