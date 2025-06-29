package com.pokeapi.service.integration.infrastructure.client;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.client.PokeapiService;
import com.pokeapi.service.infrastructure.client.model.GetAllPokemonResponse;
import com.pokeapi.service.infrastructure.client.model.GetPokemonResponse;
import com.pokeapi.service.infrastructure.client.model.ResultItem;
import com.pokeapi.service.infrastructure.client.model.Sprite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;

/**
 * PokeapiServiceTest
 *
 * @author Jean
 */
@SpringBootTest
@ActiveProfiles("test")
public class PokeapiServiceTest {

    @Autowired
    private PokeapiService pokeapiService;

    @Test
    @DisplayName("should return a list with all Pokémon detailed")
    void should_return_a_list_with_all_pokemon_detailed() {
        // Given
        final List<Pokemon> expectedPokemonList = List.of(
                new Pokemon(1, "bulbasaur", 7, 69, 64,
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                new Pokemon(2, "ivysaur", 10, 130, 142,
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/2.png"),
                new Pokemon(3, "venusaur", 20, 1000, 236,
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png")
        );

        // When
        List<Pokemon> resultPokemonList = pokeapiService.getAllPokemon();

        // Then
        Assertions.assertNotNull(resultPokemonList);
        Assertions.assertEquals(expectedPokemonList.size(), resultPokemonList.size());
        Assertions.assertEquals(new HashSet<>(expectedPokemonList), new HashSet<>(resultPokemonList));
    }

    @Test
    @DisplayName("should fetch a simple Pokémon list")
    void should_fetch_simple_pokemon_list() {
        // Given
        final GetAllPokemonResponse expectedResponse = new GetAllPokemonResponse(
                1302,
                "https://pokeapi.co/api/v2/pokemon?offset=3&limit=3",
                null,
                List.of(
                        new ResultItem("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                        new ResultItem("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/"),
                        new ResultItem("venusaur", "https://pokeapi.co/api/v2/pokemon/3/")
                )
        );

        // When
        GetAllPokemonResponse resultPokemonResponse = pokeapiService.fetchAllPokemon()
                .block();

        // Then
        Assertions.assertNotNull(resultPokemonResponse);
        Assertions.assertEquals(expectedResponse.results().size(), resultPokemonResponse.results().size());
        Assertions.assertEquals(new HashSet<>(expectedResponse.results()), new HashSet<>(resultPokemonResponse.results()));
        Assertions.assertEquals(expectedResponse.count(), resultPokemonResponse.count());
        Assertions.assertEquals(expectedResponse.next(), resultPokemonResponse.next());
        Assertions.assertEquals(expectedResponse.previous(), resultPokemonResponse.previous());
    }

    @Test
    void fetchPokemonByUriPathReturnsPokemonResponse() {
        // Given
        final GetPokemonResponse expectedPokemon = new GetPokemonResponse(3, "venusaur", 20, 1000, 236,
                new Sprite("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"));

        // When
        GetPokemonResponse resultPokemon = pokeapiService.fetchPokemonByUriPath("/pokemon/3")
                .block();

        // Then
        Assertions.assertNotNull(resultPokemon);
        Assertions.assertEquals(expectedPokemon, resultPokemon);
    }


}
