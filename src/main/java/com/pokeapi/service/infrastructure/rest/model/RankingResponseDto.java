package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * RankingRequestDto
 *
 * @author Jean
 */
public record RankingResponseDto(
        @JsonProperty("count") Integer count,
        @JsonProperty("next_page") String nextPage,
        @JsonProperty("previous_page") String previousPage,
        @JsonProperty("results") List<PokemonDto> results,
        @JsonProperty("error") String errorMessage
) {

}
