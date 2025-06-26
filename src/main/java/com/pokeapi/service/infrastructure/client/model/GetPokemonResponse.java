package com.pokeapi.service.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * GetPokemonResponse
 *
 * @author Jean
 */
@Data
@Builder
public class GetPokemonResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("height")
    private int height;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("base_experience")
    private int baseExperience;

    @JsonProperty("sprites")
    private Sprite sprites;

}
