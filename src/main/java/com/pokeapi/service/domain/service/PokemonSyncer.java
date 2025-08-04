package com.pokeapi.service.domain.service;

/**
 * PokemonSyncer
 *
 * @author Jean
 */
public interface PokemonSyncer {
    /**
     * Synchronizes Pokémon data with the external source.
     * It compares the existing Pokémon and syncs the non-existing by sending a sync notification.
     *
     * @param targetEndpoint the endpoint to use for processing the individual syncs.
     */
    void syncAllPokemon(String targetEndpoint);

    /**
     * Synchronizes a Pokémon with the external source.
     * It checks if the Pokémon exists, and if it doesn't it syncs its data.
     *
     * @param pokemonId the Pokémon Id.
     */
    void syncPokemonById(String pokemonId);

}
