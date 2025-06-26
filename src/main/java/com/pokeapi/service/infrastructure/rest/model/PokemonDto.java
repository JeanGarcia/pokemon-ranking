package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PokemonDto
 *
 * @author Jean
 */
@Data
@NoArgsConstructor
public class PokemonDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("base_experience")
    private int baseExperience;

    @JsonProperty("height")
    private int height;

    @JsonProperty("sprite_url")
    private String spriteUrl;

}
