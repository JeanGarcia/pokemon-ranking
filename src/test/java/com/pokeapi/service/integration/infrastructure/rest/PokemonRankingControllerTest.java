package com.pokeapi.service.integration.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokeapi.service.application.PokemonRankingService;
import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonService;
import com.pokeapi.service.infrastructure.rest.ControllerUtils;
import com.pokeapi.service.infrastructure.rest.PokemonRankingController;
import com.pokeapi.service.infrastructure.rest.PokemonRankingMapper;
import com.pokeapi.service.infrastructure.rest.model.PokemonDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PokemonRankingControllerTest
 *
 * @author Jean
 */

@WebMvcTest(PokemonRankingController.class)
@ActiveProfiles("test")
@Import({ControllerUtils.class, PokemonRankingMapper.class, PokemonRankingService.class})
public class PokemonRankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("should return ranking response for valid request")
    void should_return_ranking_response_for_valid_request() throws Exception {
        // Given
        final List<Pokemon> pokemonList = Stream.of(
                new Pokemon(1, "bulbasaur", 7, 69, 64, "/1.png"),
                new Pokemon(2, "ivysaur", 10, 130, 142, "/2.png"),
                new Pokemon(3, "venusaur", 20, 1000, 236, "/3.png"),
                new Pokemon(4, "charmander", 6, 85, 62, "/4.png"),
                new Pokemon(5, "charmeleon", 11, 190, 142, "/5.png")
        ).collect(Collectors.toList());
        final List<PokemonDto> expectedPokemonList = List.of(
                new PokemonDto(2, "ivysaur", 142, 10, 130, "/2.png"),
                new PokemonDto(3, "venusaur", 236, 20, 1000, "/3.png"),
                new PokemonDto(5, "charmeleon", 142, 11, 190, "/5.png")
        );
        final RankingResponseDto expectedResponse = new RankingResponseDto(
                5,
                "http://localhost/pokemon/ranking?statType=height&offset=3&limit=3",
                null,
                expectedPokemonList,
                null
        );

        Mockito.when(pokemonService.getAllPokemon()).thenReturn(pokemonList);

        // When/Then
        mockMvc.perform(get("/pokemon/ranking")
                        .param("statType", "height")
                        .param("offset", "0")
                        .param("limit", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("should return 400 for invalid stat type")
    void should_return_bad_request_for_invalid_stat_type() throws Exception {
        // Given
        final String errorMsg = "Invalid stat type: invalid";
        final RankingResponseDto expectedResponse = new RankingResponseDto(null, null, null, null, errorMsg);

        mockMvc.perform(get("/pokemon/ranking")
                        .param("statType", "invalid")
                        .param("offset", "0")
                        .param("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    @DisplayName("should return 500 for internal error")
    void should_return_internal_server_error() throws Exception {
        // Given
        final String errorMsg = "Error loading Pokémon list: ";
        final RankingResponseDto expectedResponse = new RankingResponseDto(null, null, null, null, errorMsg);

        Mockito.when(pokemonService.getAllPokemon()).thenThrow(new RuntimeException("error"));

        // When/Then
        mockMvc.perform(get("/pokemon/ranking")
                        .param("statType", "height")
                        .param("offset", "0")
                        .param("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}