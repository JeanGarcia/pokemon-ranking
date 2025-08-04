package com.pokeapi.service.infrastructure.client.pokeapi;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonFetcher;
import com.pokeapi.service.infrastructure.client.pokeapi.model.GetAllPokemonResponse;
import com.pokeapi.service.infrastructure.client.pokeapi.model.GetPokemonResponse;
import com.pokeapi.service.infrastructure.client.pokeapi.model.ResultItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

/**
 * PokeapiService
 *
 * @author Jean
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PokeapiService implements PokemonFetcher {

    @Value("${pokeapi.client.max-pokemon-limit:5}")
    private int maxPokemonLimit;

    @Value("${pokeapi.client.pause-delay:1000}")
    private int pauseDelay;

    private final WebClient pokeapiClient;
    private final PokeapiMapper pokeapiMapper;

    /**
     * Retrieves all Pokémon from the PokeAPI and maps them to a list of Pokémon models.
     *
     * @return a list of Pokémon
     */
    @Override
    public List<String> getAllPokemonIds() {
        log.info("Start fetching Pokémon IDs");
        return fetchAllPokemon()
                .map(GetAllPokemonResponse::results)
                .flatMapMany(Flux::fromIterable)
                .map(ResultItem::url)
                .map(url -> url.replaceAll(".*/pokemon", "").replaceAll("/", ""))
                .collectList()
                .block();
    }

    @Override
    public Pokemon getPokemonById(String pokemonId) {
        log.info("Fetching Pokémon by ID: {}", pokemonId);
        return fetchPokemonByUriPath("/pokemon/" + pokemonId)
                .map(pokeapiMapper::toPokemon)
                .block();
    }

    /**
     * Fetches all Pokémon from the PokeAPI.
     * Set limit is 2000 since there's currently 1302 Pokémon and is not something that will increase frequently.
     *
     * @return a Mono containing the response with all Pokémon
     */
    public Mono<GetAllPokemonResponse> fetchAllPokemon() {
        log.info("Fetching Pokémon simple list from PokeAPI with limit {}", maxPokemonLimit);
        return pokeapiClient.get()
                .uri("/pokemon?offset=0&limit={maxPokemonLimit}", maxPokemonLimit)
                .retrieve()
                .bodyToMono(GetAllPokemonResponse.class)
                .retryWhen(
                        reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(2))
                                .doBeforeRetry(retrySignal -> log.warn("Retrying fetchAllPokemon due to error: {%s}", retrySignal.failure()))
                );
    }

    /**
     * Fetches a Pokémon by its URI path from the PokeAPI.
     *
     * @param uriPath the URI path of the Pokémon to fetch, typically in the format "/pokemon/{id}"
     * @return a Mono containing the response with the Pokémon
     */
    public Mono<GetPokemonResponse> fetchPokemonByUriPath(String uriPath) {
        log.info("Fetching single Pokémon by URI path: {}", uriPath);
        return pokeapiClient.get()
                .uri(uriPath)
                .retrieve()
                .bodyToMono(GetPokemonResponse.class)
                .retryWhen(
                        reactor.util.retry.Retry.backoff(3, Duration.ofSeconds(2))
                                .doBeforeRetry(retrySignal -> log.warn("Retrying fetchPokemonByUriPath due to error: {%s}", retrySignal.failure()))
                );
    }

}
