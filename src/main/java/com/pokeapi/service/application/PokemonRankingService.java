package com.pokeapi.service.application;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.model.StatType;
import com.pokeapi.service.domain.service.PokemonService;
import com.pokeapi.service.domain.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PokemonService
 *
 * @author Jean
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class PokemonRankingService implements RankingService {

    private final PokemonService pokemonService;
    private volatile boolean isLoadingPokemonList;

    /**
     * Get a ranking of Pokémon by a specific stat type.
     * @param statType the type of stat to rank by (e.g., "weight", "height", "base_experience")
     * @param offset the starting index for pagination
     * @param limit the maximum number of Pokémon to return
     * @return a list of Pokémon ranked by the specified stat type, paginated by offset and limit.
     */
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
        return pokemonList.subList(offset, end);
    }

    @Cacheable("pokemonList")
    @Override
    public List<Pokemon> getAllPokemon() {
        log.info("Cache missed, fetching all Pokémon from external service");
        if (isLoadingPokemonList) {
            log.warn("Pokémon list is currently loading, returning null to avoid caching");
            return null;
        }
        isLoadingPokemonList = true;
        try {
            return pokemonService.getAllPokemon();
        } catch (Exception e) {
            log.error("Error loading Pokémon list: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            isLoadingPokemonList = false;
        }
    }

    private static void sortPokemonListByStatType(StatType statType, List<Pokemon> pokemonList) {
        pokemonList.sort(
                (p1, p2) -> switch (statType) {
                    case StatType.WEIGHT -> Integer.compare(p2.weight(), p1.weight());
                    case StatType.HEIGHT -> Integer.compare(p2.height(), p1.height());
                    case StatType.BASE_EXPERIENCE -> Integer.compare(p2.baseExperience(), p1.baseExperience());
        });
    }

    private static StatType validateAndReturnStatType(String statType) {
        if (statType == null || statType.isBlank()) {
            throw new IllegalArgumentException("Stat type cannot be null or blank");
        }
        try {
            return StatType.valueOf(statType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid stat type: " + statType);
        }
    }

    private static void validateOffsetAndLimit(int offset, int limit, int size) {
        if (offset < 0 || limit <= 0 || offset >= size) {
            throw new IllegalArgumentException("Invalid offset or limit");
        }
    }



}
