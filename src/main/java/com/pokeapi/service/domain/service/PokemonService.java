package com.pokeapi.service.domain.service;

import com.pokeapi.service.domain.model.Pokemon;

import java.util.List;

/**
 * PokemonService
 *
 * @author Jean
 */
public interface PokemonService {

    /**
     * Retrieves a simple list of all Pokémon.
     *
     * @return a list of all Pokémon
     */
    List<Pokemon> getAllPokemon();

}
