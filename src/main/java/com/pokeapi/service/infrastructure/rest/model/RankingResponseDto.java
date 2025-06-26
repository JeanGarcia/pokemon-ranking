package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * RankingRequestDto
 *
 * @author Jean
 */
@Data
@Builder
public class RankingResponseDto {

        @JsonProperty("count")
        private int count;

        @JsonProperty("next_page")
        private String nextPage;

        @JsonProperty("previous_page")
        private String previousPage;

        @JsonProperty("results")
        private List<PokemonDto> results;
}
