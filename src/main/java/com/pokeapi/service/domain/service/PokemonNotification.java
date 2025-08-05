package com.pokeapi.service.domain.service;

import java.net.URI;
import java.util.List;

/**
 * PokemonNotification
 *
 * @author Jean
 */
public interface PokemonNotification {

    /**
     * Sends a bulk notification to specific target
     *
     * @param pokemonIds     all the ids to send
     * @param targetEndpoint the target endpoint.
     */
    void sendPokemonSyncNotificationBulk(List<String> pokemonIds, URI targetEndpoint);

}
