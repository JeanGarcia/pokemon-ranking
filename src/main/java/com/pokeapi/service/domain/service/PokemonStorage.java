package com.pokeapi.service.domain.service;

import com.pokeapi.service.domain.model.Pokemon;

import java.util.List;
import java.util.Optional;

/**
 * PokemonStorage
 *
 * @author Jean
 */
public interface PokemonStorage {

    /**
     * Retrieves a Pokémon list from the storage.
     *
     * @return a Pokémon list
     */
    List<Pokemon> retrievePokemonList();

    /**
     * Returns a Pokémon by Id.
     *
     * @param pokemonId Pokémon Id.
     * @return an Optional object which can contain a Pokémon if exists.
     */
    Optional<Pokemon> retrievePokemon(String pokemonId);

    /**
     * Saves a Pokémon in the storage
     *
     * @param pokemon Pokémon to be saved.
     */
    void savePokemon(Pokemon pokemon);
}
