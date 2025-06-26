package com.pokeapi.service.infrastructure.client;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.client.model.GetPokemonResponse;
import org.springframework.stereotype.Component;

/**
 * PokeapiMapper
 *
 * @author Jean
 */
@Component
public class PokeapiMapper {

    /**
     * Converts a GetPokemonResponse (Pokeapi) to a Pokémon model.
     *
     * @param getPokemonResponse the response from the PokeAPI
     * @return the converted Pokémon model
     */
    public Pokemon toPokemon(GetPokemonResponse getPokemonResponse) {
        if (getPokemonResponse == null) {
            return null;
        }
        String spriteUrl = getPokemonResponse.getSprites() != null
                ? getPokemonResponse.getSprites().getFrontDefault()
                : null;
        return new Pokemon(
                getPokemonResponse.getId(),
                getPokemonResponse.getName(),
                getPokemonResponse.getHeight(),
                getPokemonResponse.getWeight(),
                getPokemonResponse.getBaseExperience(),
                spriteUrl
        );
    }

}
