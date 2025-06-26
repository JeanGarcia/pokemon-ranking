package com.pokeapi.service.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * SpriteResponse
 *
 * @author Jean
 */
@Data
public class Sprite {

    @JsonProperty("front_default")
    private String frontDefault;

}
