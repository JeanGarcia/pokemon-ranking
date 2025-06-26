package com.pokeapi.service.infrastructure.client;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonService;
import com.pokeapi.service.infrastructure.client.model.GetAllPokemonResponse;
import com.pokeapi.service.infrastructure.client.model.GetPokemonResponse;
import com.pokeapi.service.infrastructure.client.model.ResultItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * PokeapiService
 *
 * @author Jean
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PokeapiService implements PokemonService {

    private static final int MAX_POKEMON_LIMIT = 5;
    private final WebClient pokeapiClient;
    private final PokeapiMapper pokeapiMapper;

    /**
     * Retrieves all Pokémon from the PokeAPI and maps them to a list of Pokémon models.
     *
     * @return a list of Pokémon
     */
    @Override
    public List<Pokemon> getAllPokemon() {
        return fetchAllPokemon()
                .map(GetAllPokemonResponse::getResults)
                .flatMapMany(Flux::fromIterable)
                .map(ResultItem::getUrl)
                .map(url -> url.replaceAll(".*/pokemon", "/pokemon"))
                .flatMap(this::fetchPokemonByUriPath)
                .map(pokeapiMapper::toPokemon)
                .collectList()
                .block();
    }

    /**
     * Fetches all Pokémon from the PokeAPI.
     * Set limit is 2000 since there's currently 1302 Pokémon and is not something that will increase frequently.
     *
     * @return a Mono containing the response with all Pokémon
     */
    public Mono<GetAllPokemonResponse> fetchAllPokemon() {
        log.info("Fetching all Pokémon from PokeAPI with limit {}", MAX_POKEMON_LIMIT);
        return pokeapiClient.get()
                .uri("/pokemon?offset=0&limit={MAX_POKEMON_LIMIT}", MAX_POKEMON_LIMIT)
                .retrieve()
                .bodyToMono(GetAllPokemonResponse.class);
    }

    /**
     * Fetches a Pokémon by its URI path from the PokeAPI.
     *
     * @param uriPath the URI path of the Pokémon to fetch, typically in the format "/pokemon/{id}"
     * @return a Mono containing the response with the Pokémon
     */
    public Mono<GetPokemonResponse> fetchPokemonByUriPath(String uriPath) {
        pause();
        log.info("Fetching Pokémon by URI path: {}", uriPath);
        return pokeapiClient.get()
                .uri(uriPath)
                .retrieve()
                .bodyToMono(GetPokemonResponse.class);
    }

    /**
     * Pauses the execution for a short duration to avoid hitting the API too quickly.
     * This is useful to respect rate limits imposed by the PokeAPI.
     */
    private void pause() {
        try {
            Thread.sleep(1000); // Wait for 1 second to avoid hitting the API too quickly
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

}
