package com.pokeapi.service.domain.service;


import com.pokeapi.service.domain.model.Pokemon;

import java.util.List;

/**
 * PokemonRanker
 *
 * @author Jean
 */
public interface PokemonRanker {

    /**
     * Ranks a list of Pokémon by a specified stat type.
     *
     * @param pokemonList the list of Pokémon to rank
     * @param statType    the stat type to rank by (e.g., "attack", "defense", etc.)
     * @param offset      the offset for pagination
     * @param limit       the maximum number of Pokémon to return
     * @return a ranked list of Pokémon based on the specified stat type
     */
    List<Pokemon> rankPokemonListByStat(List<Pokemon> pokemonList, String statType, int offset, int limit);

    /**
     * Retrieves all Pokémon.
     *
     * @return a list of all Pokémon
     */
    List<Pokemon> getAllPokemon();

}
