package com.pokeapi.service.unit.application;

import com.pokeapi.service.application.PokemonRankingService;
import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PokemonRankingServiceTest
 *
 * @author Jean
 */
@ExtendWith(MockitoExtension.class)
public class PokemonRankingServiceTest {

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private PokemonRankingService pokemonRankingService;

    private static final List<Pokemon> POKEMON_LIST = Stream.of(
            new Pokemon(1, "Pikachu", 100, 50, 30, "url/1"),
            new Pokemon(2, "Bulbasaur", 80, 60, 40, "url/2"),
            new Pokemon(3, "Charmander", 90, 70, 20, "url/3"),
            new Pokemon(4, "Squirtle", 70, 80, 50, "url/4"),
            new Pokemon(5, "Jigglypuff", 60, 90, 10, "url/5"),
            new Pokemon(6, "Meowth", 110, 40, 60, "url/6"),
            new Pokemon(7, "Eevee", 120, 30, 70, "url/7"),
            new Pokemon(8, "Snorlax", 130, 100, 80, "url/8"),
            new Pokemon(9, "Fearow", 35, 22, 32, "url/9"),
            new Pokemon(10, "Sandshrew", 55, 44, 80, "url/10")
    ).collect(Collectors.toList());


    public static Stream<Arguments> givenPokemonRankedByStat() {
        return Stream.of(
                Arguments.of(POKEMON_LIST, "height", 0, 5, List.of(
                        new Pokemon(8, "Snorlax", 130, 100, 80, "url/8"),
                        new Pokemon(7, "Eevee", 120, 30, 70, "url/7"),
                        new Pokemon(6, "Meowth", 110, 40, 60, "url/6"),
                        new Pokemon(1, "Pikachu", 100, 50, 30, "url/1"),
                        new Pokemon(3, "Charmander", 90, 70, 20, "url/3")
                )),
                Arguments.of(POKEMON_LIST, "weight", 0, 5, List.of(
                        new Pokemon(8, "Snorlax", 130, 100, 80, "url/8"),
                        new Pokemon(5, "Jigglypuff", 60, 90, 10, "url/5"),
                        new Pokemon(4, "Squirtle", 70, 80, 50, "url/4"),
                        new Pokemon(3, "Charmander", 90, 70, 20, "url/3"),
                        new Pokemon(2, "Bulbasaur", 80, 60, 40, "url/2")
                )),
                Arguments.of(POKEMON_LIST, "base_experience", 0, 5, List.of(
                        new Pokemon(8, "Snorlax", 130, 100, 80, "url/8"),
                        new Pokemon(10, "Sandshrew", 55, 44, 80, "url/10"),
                        new Pokemon(7, "Eevee", 120, 30, 70, "url/7"),
                        new Pokemon(6, "Meowth", 110, 40, 60, "url/6"),
                        new Pokemon(4, "Squirtle", 70, 80, 50, "url/4")
                )),
                Arguments.of(null, "base_experience", 0, 5, List.of())
        );
    }

    @ParameterizedTest()
    @MethodSource("givenPokemonRankedByStat")
    public void should_rank_pokemon_list_by_stat(
            List<Pokemon> intialPokemonList,
            String statType,
            int offset, int limit,
            List<Pokemon> expectedRankedList
    ) {
        // When
        List<Pokemon> rankedList = pokemonRankingService.rankPokemonListByStat(intialPokemonList, statType, offset, limit);

        // Then
        Assertions.assertEquals(expectedRankedList, rankedList);
    }

}
