package com.pokeapi.service.domain.service;

import com.pokeapi.service.domain.model.Pokemon;

import java.util.List;

/**
 * PokemonFetcher
 *
 * @author Jean
 */
public interface PokemonFetcher {

    /**
     * Fetches a list of all Pokémon IDs.
     *
     * @return a list of Pokémon IDs
     */
    List<String> getAllPokemonIds();

    /**
     * Fetches one Pokémon by ID.
     *
     * @param pokemonId the Pokémon ID.
     * @return a Pokémon
     */
    Pokemon getPokemonById(String pokemonId);

}
