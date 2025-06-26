package com.pokeapi.service.infrastructure.rest;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.rest.model.PokemonDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RankingMapper
 *
 * @author Jean
 */
@Component
public class PokemonRankingMapper {

    /**
     * Unify several data into a RankingRequestDto.
     *
     * @param results      a list of PokemonDto results
     * @param count        the total count of Pokémon
     * @param nextPage     the URL for the next page of results
     * @param previousPage the URL for the previous page of results
     * @return a RankingResponseDto containing the results, count, next page, and previous page
     */
    public RankingResponseDto toRankingResponseDto(List<Pokemon> results,
                                                   int count,
                                                   String nextPage,
                                                   String previousPage) {
        List<PokemonDto> pokemonDtoList = toPokemonDtoList(results);
        return RankingResponseDto.builder()
                .results(pokemonDtoList)
                .count(count)
                .nextPage(nextPage)
                .previousPage(previousPage)
                .build();
    }

    /**
     * Converts a list of Pokémon models to a list of Pokémon DTOs.
     *
     * @param pokemonList the list of Pokémon models
     * @return the converted list of Pokémon DTOs
     */
    public List<PokemonDto> toPokemonDtoList(List<Pokemon> pokemonList) {
        return pokemonList.stream()
                .map(this::toPokemonDto)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Pokémon model to a Pokémon DTO.
     *
     * @param pokemon the Pokémon model
     * @return the converted Pokémon DTO
     */
    public PokemonDto toPokemonDto(Pokemon pokemon) {
        return PokemonDto.builder()
                .id(pokemon.id())
                .name(pokemon.name())
                .height(pokemon.height())
                .weight(pokemon.weight())
                .baseExperience(pokemon.baseExperience())
                .spriteUrl(pokemon.spriteUrl())
                .build();
    }

}
