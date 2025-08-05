package com.pokeapi.service.application;

import com.pokeapi.service.domain.exception.InvalidArgumentException;
import com.pokeapi.service.domain.exception.NoPokemonException;
import com.pokeapi.service.domain.exception.PokemonSyncFailedException;
import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.model.StatType;
import com.pokeapi.service.domain.service.PokemonFetcher;
import com.pokeapi.service.domain.service.PokemonNotification;
import com.pokeapi.service.domain.service.PokemonRanker;
import com.pokeapi.service.domain.service.PokemonStorage;
import com.pokeapi.service.domain.service.PokemonSyncer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * PokemonRankingService
 *
 * @author Jean
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PokemonRankingService implements PokemonRanker, PokemonSyncer {

    private static final String INVALID_STAT_TYPE_ERROR_MSG = "Invalid stat type: ";
    public static final String POKEMON_LIST_EMPTY_MSG = "Pokémon list is empty try loading the Pokemon list first";

    private final PokemonStorage pokemonStorage;
    private final PokemonFetcher pokemonFetcher;
    private final PokemonNotification pokemonNotification;


    @Override
    public List<Pokemon> rankPokemonListByStat(List<Pokemon> pokemonList, String statType, int offset, int limit) {
        if (pokemonList == null) {
            return List.of();
        }

        StatType type = validateAndReturnStatType(statType);
        validateOffsetAndLimit(offset, limit, pokemonList.size());
        sortPokemonListByStatType(type, pokemonList);

        // Ensure the offset and limit are within bounds
        int end = Math.min(offset + limit, pokemonList.size());
        return new ArrayList<>(pokemonList.subList(offset, end));
    }

    @Override
    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemonList = pokemonStorage.retrievePokemonList();

        if (pokemonList.isEmpty()) {
            log.warn(POKEMON_LIST_EMPTY_MSG);
            throw new NoPokemonException(POKEMON_LIST_EMPTY_MSG);
        }
        return pokemonList;
    }

    @Override
    public void syncAllPokemon(URI targetEndpoint) {
        try {
            final List<String> pokemonIds = pokemonFetcher.getAllPokemonIds();
            final List<String> existingPokemonIds = pokemonStorage.retrievePokemonList()
                    .stream()
                    .map(Pokemon::getId)
                    .map(String::valueOf)
                    .toList();
            List<String> nonExistingPokemonIds = removePokemonIdsFromList(pokemonIds, existingPokemonIds);
            log.info("The Pokémon ids ready to be synced: {}", nonExistingPokemonIds);
            pokemonNotification.sendPokemonSyncNotificationBulk(nonExistingPokemonIds, targetEndpoint);
        } catch (Exception e) {
            log.error("Error syncing Pokémon data: ", e);
            throw new PokemonSyncFailedException("Error syncing Pokémon data: " + e.getMessage(), e);
        }
    }

    @Override
    public void syncPokemonById(String pokemonId) {
        try {
            final Optional<Pokemon> existingPokemonList = pokemonStorage.retrievePokemon(pokemonId);

            if (existingPokemonList.isPresent()) {
                log.info("Pokémon with ID {} is already stored, skipping sync.", pokemonId);
                return;
            }

            final Pokemon pokemon = pokemonFetcher.getPokemonById(pokemonId);
            if (pokemon != null) {
                log.info("Saving Pokémon with ID {}", pokemonId);
                pokemonStorage.savePokemon(pokemon);
            }
        } catch (Exception e) {
            log.error("Error syncing Pokémon with ID {}", pokemonId, e);
            throw new PokemonSyncFailedException("Error syncing Pokémon with ID " + pokemonId + e.getMessage(), e);
        }
    }

    private List<String> removePokemonIdsFromList(List<String> pokemonIds, List<String> existingPokemonIds) {
        return pokemonIds.stream()
                .filter(id -> !existingPokemonIds.contains(id))
                .toList();
    }

    /**
     * Sorts the Pokémon list based on the specified stat type.
     * The sorting is done in descending order.
     *
     * @param statType    the type of stat to sort by (e.g., weight, height, base_experience)
     * @param pokemonList the list of Pokémon to sort
     */
    private static void sortPokemonListByStatType(StatType statType, List<Pokemon> pokemonList) {
        pokemonList.sort(
                (p1, p2) -> switch (statType) {
                    case StatType.WEIGHT -> Integer.compare(p2.getWeight(), p1.getWeight());
                    case StatType.HEIGHT -> Integer.compare(p2.getHeight(), p1.getHeight());
                    case StatType.BASE_EXPERIENCE -> Integer.compare(p2.getBaseExperience(), p1.getBaseExperience());
                });
    }

    /**
     * Validates the stat type and returns the corresponding StatType enum.
     *
     * @param statType the stat type as a string
     * @return the corresponding StatType enum
     * @throws InvalidArgumentException if the stat type is null, blank, or invalid
     */
    private static StatType validateAndReturnStatType(String statType) {
        if (statType == null || statType.isBlank()) {
            throw new InvalidArgumentException(INVALID_STAT_TYPE_ERROR_MSG + statType);
        }
        try {
            return StatType.valueOf(statType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentException(INVALID_STAT_TYPE_ERROR_MSG + statType);
        }
    }

    /**
     * Validates the offset and limit parameters.
     *
     * @param offset the starting index for pagination
     * @param limit  the maximum number of Pokémon to return
     * @param size   the total size of the Pokémon list
     * @throws InvalidArgumentException if the offset or limit is invalid
     */
    private static void validateOffsetAndLimit(int offset, int limit, int size) {
        if (offset < 0 || limit <= 0 || offset >= size) {
            throw new InvalidArgumentException("Invalid offset or limit");
        }
    }

}
