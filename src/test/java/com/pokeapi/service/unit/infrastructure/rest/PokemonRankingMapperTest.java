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

import java.util.List;

/**
 * PokeapiMapperTest
 *
 * @author Jean
 */
@SpringBootTest
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
        Assertions.assertEquals(POKEMON_LIST.size(), rankingResponseDto.getResults().size());
        Assertions.assertEquals(count, rankingResponseDto.getCount());
        Assertions.assertEquals(nextPage, rankingResponseDto.getNextPage());
        Assertions.assertEquals(previousPage, rankingResponseDto.getPreviousPage());
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
        Assertions.assertEquals(pokemon.id(), pokemonDto.getId());
        Assertions.assertEquals(pokemon.name(), pokemonDto.getName());
        Assertions.assertEquals(pokemon.height(), pokemonDto.getHeight());
        Assertions.assertEquals(pokemon.weight(), pokemonDto.getWeight());
        Assertions.assertEquals(pokemon.baseExperience(), pokemonDto.getBaseExperience());
        Assertions.assertEquals(pokemon.spriteUrl(), pokemonDto.getSpriteUrl());
    }
}
