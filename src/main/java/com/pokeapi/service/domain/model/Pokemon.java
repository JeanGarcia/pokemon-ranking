package com.pokeapi.service.domain.model;

/**
 * Pokemon
 *
 * @author Jean
 */

public record Pokemon (
        int id,
        String name,
        int height,
        int weight,
        int baseExperience,
        String spriteUrl
) {

}
