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
        GetPokemonResponse getPokemonResponse = GetPokemonResponse.builder()
                .id(25)
                .name("pikachu")
                .height(4)
                .weight(60)
                .baseExperience(112)
                .sprites(Sprite.builder()
                        .frontDefault("front_default.png")
                        .build()
                )
                .build();

        // When
        Pokemon pokemon = pokeapiMapper.toPokemon(getPokemonResponse);

        // Then
        Assertions.assertEquals(getPokemonResponse.getName(), pokemon.name());
        Assertions.assertEquals(getPokemonResponse.getId(), pokemon.id());
        Assertions.assertEquals(getPokemonResponse.getWeight(), pokemon.weight());
        Assertions.assertEquals(getPokemonResponse.getHeight(), pokemon.height());
        Assertions.assertEquals(getPokemonResponse.getBaseExperience(), pokemon.baseExperience());
        Assertions.assertEquals(getPokemonResponse.getSprites().getFrontDefault(), pokemon.spriteUrl());
    }
}
