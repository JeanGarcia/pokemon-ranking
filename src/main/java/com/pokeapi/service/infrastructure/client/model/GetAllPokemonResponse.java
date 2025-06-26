package com.pokeapi.service.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * GetPokemonListResponse
 *
 * @author Jean
 */
@Data
public class GetAllPokemonResponse {

    @JsonProperty("count")
    private int count;

    @JsonProperty("next")
    private String next;

    @JsonProperty("previous")
    private String previous;

    @JsonProperty("results")
    private List<ResultItem> results;

}
