package com.pokeapi.service.unit.application;

import com.pokeapi.service.application.PokemonRankingService;
import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
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

    @ParameterizedTest(name = "should rank pokemon list by {0} with offset {1} and limit {2}")
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

    @Test
    @DisplayName("should return empty list when pokemon list is null")
    public void should_return_all_pokemon() {
        // Given
        List<Pokemon> expectedPokemonList = POKEMON_LIST;
        Mockito.when(pokemonService.getAllPokemon()).thenReturn(expectedPokemonList);

        // When
        List<Pokemon> actualPokemonList = pokemonRankingService.getAllPokemon();

        // Then
        Assertions.assertEquals(expectedPokemonList, actualPokemonList);
    }

    @Test
    @DisplayName("should return null when pokemon list is loading")
    public void should_return_null_when_pokemon_list_is_loading() throws NoSuchFieldException, IllegalAccessException {
        // Given
        // Set isLoadingPokemonList to true
        Field field = PokemonRankingService.class.getDeclaredField("isLoadingPokemonList");
        field.setAccessible(true);
        field.set(pokemonRankingService, true);

        try {
            // When
            List<Pokemon> actualPokemonList = pokemonRankingService.getAllPokemon();

            // Then
            Assertions.assertNull(actualPokemonList);
        } finally {
            // Reset isLoadingPokemonList to false
            field.set(pokemonRankingService, false);
        }
    }

    @Test
    @DisplayName("should throw exception when unable to load Pokémon list")
    public void should_throw_exception_when_unable_to_load_pokemon_list() {
        // Given
        Mockito.when(pokemonService.getAllPokemon()).thenThrow(new RuntimeException("Error"));

        // When & Then
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> pokemonRankingService.getAllPokemon());
        Assertions.assertTrue(exception.getMessage().contains("Error loading Pokémon list"));

    }

    public static Stream<Arguments> givenInvalidStatTypes() {
        return Stream.of(
                Arguments.of("invalid_stat_type", "Invalid stat type: invalid_stat_type"),
                Arguments.of(null, "Invalid stat type: null"),
                Arguments.of("", "Invalid stat type:")
        );
    }

    @ParameterizedTest(name = "should throw exception when stat type is invalid: {0} . With Error Message: {1}")
    @MethodSource("givenInvalidStatTypes")
    public void should_throw_exception_when_stat_type_is_invalid(String statType, String errorMessage) {
        // When & Then
        IllegalArgumentException blankException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> pokemonRankingService.rankPokemonListByStat(POKEMON_LIST, statType, 0, 5)
        );
        Assertions.assertTrue(blankException.getMessage().contains(errorMessage));
    }

    public static Stream<Arguments> givenInvalidOffsetAndLimit() {
        return Stream.of(
                Arguments.of(-1, 5), // Invalid offset
                Arguments.of(0, -5), // Invalid limit
                Arguments.of(10, 5) // Offset greater than list size
        );
    }

    @ParameterizedTest(name = "should throw exception when offset or limit is invalid: offset={0}, limit={1}")
    @MethodSource("givenInvalidOffsetAndLimit")
    public void should_throw_exception_when_offset_or_limit_is_invalid(int offset, int limit) {
        // Given
        String statType = "height";

        // When & Then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> pokemonRankingService.rankPokemonListByStat(POKEMON_LIST, statType, offset, limit)
        );
        Assertions.assertTrue(exception.getMessage().contains("Invalid offset or limit"));
    }

}
