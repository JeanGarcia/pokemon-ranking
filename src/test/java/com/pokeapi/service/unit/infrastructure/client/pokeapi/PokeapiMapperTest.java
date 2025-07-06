package com.pokeapi.service.unit.infrastructure.client.pokeapi;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.infrastructure.client.pokeapi.PokeapiMapper;
import com.pokeapi.service.infrastructure.client.pokeapi.model.GetPokemonResponse;
import com.pokeapi.service.infrastructure.client.pokeapi.model.Sprite;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * PokeapiMapperTest
 *
 * @author Jean
 */
@SpringBootTest
@ActiveProfiles("test")
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
        Assertions.assertEquals(getPokemonResponse.name(), pokemon.getName());
        Assertions.assertEquals(getPokemonResponse.id(), pokemon.getId());
        Assertions.assertEquals(getPokemonResponse.weight(), pokemon.getWeight());
        Assertions.assertEquals(getPokemonResponse.height(), pokemon.getHeight());
        Assertions.assertEquals(getPokemonResponse.baseExperience(), pokemon.getBaseExperience());
        Assertions.assertEquals(getPokemonResponse.sprites().frontDefault(), pokemon.getSpriteUrl());
    }
}
