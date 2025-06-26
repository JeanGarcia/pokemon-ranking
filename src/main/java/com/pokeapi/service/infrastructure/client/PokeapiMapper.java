package com.pokeapi.service.infrastructure.client;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.client.model.GetPokemonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * PokeapiMapper
 *
 * @author Jean
 */
@Mapper
public interface PokeapiMapper {

    /**
     * Converts a GetPokemonResponse (Pokeapi) to a Pokémon model.
     *
     * @param getPokemonResponse the response from the PokeAPI
     * @return the converted Pokémon model
     */
    @Mapping(target = "spriteUrl", expression = "java(getPokemonResponse.getSprites() != null ? " +
            "getPokemonResponse.getSprites().getFrontDefault() : null)")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "height", source = "height")
    @Mapping(target = "weight", source = "weight")
    @Mapping(target = "baseExperience", source = "baseExperience")
    Pokemon toPokemon(GetPokemonResponse getPokemonResponse);

}
