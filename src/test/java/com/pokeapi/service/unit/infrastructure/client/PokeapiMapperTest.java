package com.pokeapi.service.unit.infrastructure.client;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.client.PokeapiMapper;
import com.pokeapi.service.infrastructure.client.model.GetPokemonResponse;
import com.pokeapi.service.infrastructure.client.model.Sprite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * PokeapiMapperTest
 *
 * @author Jean
 */
@SpringBootTest
public class PokeapiMapperTest {

    @Autowired
    private PokeapiMapper pokeapiMapper;


    @Test
    @DisplayName("should map GetPokemonResponse to Pokemon")
    public void should_map_to_Pokemon() {
        // Given

        GetPokemonResponse getPokemonResponse = new GetPokemonResponse(25, "pikachu", 4, 60, 112,
                new Sprite("front_default.png"));

        // When
        Pokemon pokemon = pokeapiMapper.toPokemon(getPokemonResponse);

        // Then
        Assertions.assertEquals(getPokemonResponse.name(), pokemon.name());
        Assertions.assertEquals(getPokemonResponse.id(), pokemon.id());
        Assertions.assertEquals(getPokemonResponse.weight(), pokemon.weight());
        Assertions.assertEquals(getPokemonResponse.height(), pokemon.height());
        Assertions.assertEquals(getPokemonResponse.baseExperience(), pokemon.baseExperience());
        Assertions.assertEquals(getPokemonResponse.sprites().frontDefault(), pokemon.spriteUrl());
    }
}
