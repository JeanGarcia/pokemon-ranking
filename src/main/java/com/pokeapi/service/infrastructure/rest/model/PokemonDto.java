package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * PokemonDto
 *
 * @author Jean
 */
@Data
@Builder
public class PokemonDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("base_experience")
    private int baseExperience;

    @JsonProperty("height")
    private int height;

    @JsonProperty("weight")
    private int weight;

    @JsonProperty("sprite_url")
    private String spriteUrl;

}
