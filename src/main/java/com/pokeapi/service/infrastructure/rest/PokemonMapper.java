package com.pokeapi.service.infrastructure.rest;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.rest.model.PokemonDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * RankingMapper
 *
 * @author Jean
 */
@Mapper
public interface PokemonMapper {

    /**
     * Unify several data into a RankingRequestDto.
     * @param results a list of PokemonDto results
     * @param count the total count of Pokémon
     * @param nextPage the URL for the next page of results
     * @param previousPage the URL for the previous page of results
     * @return a RankingResponseDto containing the results, count, next page, and previous page
     */
    @Mapping(target = "results", source = "results")
    @Mapping(target = "count", source = "count")
    @Mapping(target = "nextPage", source = "nextPage")
    @Mapping(target = "previousPage", source = "previousPage")
    RankingResponseDto toRankingResponseDto(List<Pokemon> results,
                                            int count,
                                            String nextPage,
                                            String previousPage);

    /**
     * Converts a list of Pokémon models to a list of Pokémon DTOs.
     *
     * @param pokemonList the list of Pokémon models
     * @return the converted list of Pokémon DTOs
     */
    List<PokemonDto> toPokemonDtoList(List<Pokemon> pokemonList);

    /**
     * Converts a Pokémon model to a Pokémon DTO.
     *
     * @param pokemon the Pokémon model
     * @return the converted Pokémon DTO
     */
    @Mapping(target = "spriteUrl", source = "spriteUrl")
    PokemonDto toPokemonDto(Pokemon pokemon);

}
