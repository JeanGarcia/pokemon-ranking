package com.pokeapi.service.unit.infrastructure.rest;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.rest.PokemonRankingMapper;
import com.pokeapi.service.infrastructure.rest.model.PokemonDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * PokemonRankingMapperTest
 *
 * @author Jean
 */
@SpringBootTest
@ActiveProfiles("test")
public class PokemonRankingMapperTest {

    @Autowired
    private PokemonRankingMapper pokemonRankingMapper;

    private static final List<Pokemon> POKEMON_LIST = List.of(
            new Pokemon(1, "bulbasaur", 7, 69, 64, "/1.png"),
            new Pokemon(2, "ivysaur", 10, 130, 142, "/2.png"),
            new Pokemon(3, "venusaur", 20, 1000, 236, "/3.png"),
            new Pokemon(4, "charmander", 6, 85, 62, "/4.png"),
            new Pokemon(5, "charmeleon", 11, 190, 142, "/5.png")
    );

    @Test
    @DisplayName("should map to ranking response dto")
    public void should_map_to_ranking_response_dto() {
        // Given
        final int count = 5;
        final String nextPage = "nextPageUrl";
        final String previousPage = "previousPageUrl";

        // When
        RankingResponseDto rankingResponseDto = pokemonRankingMapper.toRankingResponseDto(POKEMON_LIST, count, nextPage, previousPage);

        // Then
        Assertions.assertEquals(POKEMON_LIST.size(), rankingResponseDto.results().size());
        Assertions.assertEquals(count, rankingResponseDto.count());
        Assertions.assertEquals(nextPage, rankingResponseDto.nextPage());
        Assertions.assertEquals(previousPage, rankingResponseDto.previousPage());
    }

    @Test
    @DisplayName("should map to ranking response dto with error message")
    public void should_map_to_ranking_response_dto_with_error_message() {
        // Given
        final String errorMessage = "An error occurred while fetching the ranking";

        // When
        RankingResponseDto rankingResponseDto = pokemonRankingMapper.toRankingResponseDto(errorMessage);

        // Then
        Assertions.assertNull(rankingResponseDto.results());
        Assertions.assertNull(rankingResponseDto.count());
        Assertions.assertNull(rankingResponseDto.nextPage());
        Assertions.assertNull(rankingResponseDto.previousPage());
        Assertions.assertEquals(errorMessage, rankingResponseDto.errorMessage());
    }

    @Test
    @DisplayName("should map to pokemon dto list")
    public void should_map_to_pokemon_dto_list() {
        // When
        List<PokemonDto> pokemonDtoList = pokemonRankingMapper.toPokemonDtoList(POKEMON_LIST);

        // Then
        Assertions.assertEquals(POKEMON_LIST.size(), pokemonDtoList.size());
    }

    @Test
    @DisplayName("should map to pokemon dto")
    public void should_map_to_pokemon_dto() {
        // Given
        Pokemon pokemon = new Pokemon(1, "bulbasaur", 7, 69, 64, "/1.png");

        // When
        PokemonDto pokemonDto = pokemonRankingMapper.toPokemonDto(pokemon);

        // Then
        Assertions.assertEquals(pokemon.getId(), pokemonDto.id());
        Assertions.assertEquals(pokemon.getName(), pokemonDto.name());
        Assertions.assertEquals(pokemon.getHeight(), pokemonDto.height());
        Assertions.assertEquals(pokemon.getWeight(), pokemonDto.weight());
        Assertions.assertEquals(pokemon.getBaseExperience(), pokemonDto.baseExperience());
        Assertions.assertEquals(pokemon.getSpriteUrl(), pokemonDto.spriteUrl());
    }
}
