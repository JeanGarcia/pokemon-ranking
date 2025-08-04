package com.pokeapi.service.integration.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonFetcher;
import com.pokeapi.service.domain.service.PokemonNotification;
import com.pokeapi.service.domain.service.PokemonStorage;
import com.pokeapi.service.infrastructure.rest.model.ErrorResponseDto;
import com.pokeapi.service.infrastructure.rest.model.PokemonDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PokemonRankingControllerTest
 *
 * @author Jean
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PokemonRankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PokemonFetcher pokemonFetcher;

    @MockitoBean
    private PokemonStorage pokemonStorage;

    @MockitoBean
    private PokemonNotification pokemonNotification;

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

        Mockito.when(pokemonStorage.retrievePokemonList()).thenReturn(pokemonList);

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
        final List<Pokemon> pokemonList = Stream.of(
                new Pokemon(1, "bulbasaur", 7, 69, 64, "/1.png"),
                new Pokemon(2, "ivysaur", 10, 130, 142, "/2.png"),
                new Pokemon(3, "venusaur", 20, 1000, 236, "/3.png"),
                new Pokemon(4, "charmander", 6, 85, 62, "/4.png"),
                new Pokemon(5, "charmeleon", 11, 190, 142, "/5.png")
        ).collect(Collectors.toList());
        final ErrorResponseDto errorResponse = new ErrorResponseDto(
                "2025-08-04T20:26:52.691496400Z",
                400,
                "Bad Request",
                "Invalid stat type: invalid",
                "/pokemon/ranking"
        );
        Mockito.when(pokemonStorage.retrievePokemonList()).thenReturn(pokemonList);

        // When
        String responseJson = mockMvc.perform(get("/pokemon/ranking")
                        .param("statType", "invalid")
                        .param("offset", "0")
                        .param("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        var node = objectMapper.readTree(responseJson);
        assertEquals(errorResponse.path(), node.get("path").asText());
        assertEquals(errorResponse.status(), node.get("status").asInt());
        assertEquals(errorResponse.error(), node.get("error").asText());
        assertEquals(errorResponse.message(), node.get("message").asText());
        assertDoesNotThrow(() -> java.time.Instant.parse(node.get("timestamp").asText()));
    }

    @Test
    @DisplayName("should return 404 when the list is empty")
    void should_return_not_found_when_list_is_empty() throws Exception {
        // Given
        final ErrorResponseDto errorResponse = new ErrorResponseDto(
                "2025-08-04T20:26:52.691496400Z",
                404,
                "Not Found",
                "Pokémon list is empty try loading the Pokemon list first",
                "/pokemon/ranking"
        );
        Mockito.when(pokemonStorage.retrievePokemonList()).thenReturn(new ArrayList<>());

        // When
        String responseJson = mockMvc.perform(get("/pokemon/ranking")
                        .param("statType", "height")
                        .param("offset", "0")
                        .param("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        var node = objectMapper.readTree(responseJson);
        assertEquals(errorResponse.path(), node.get("path").asText());
        assertEquals(errorResponse.status(), node.get("status").asInt());
        assertEquals(errorResponse.error(), node.get("error").asText());
        assertEquals(errorResponse.message(), node.get("message").asText());
        assertDoesNotThrow(() -> java.time.Instant.parse(node.get("timestamp").asText()));
    }
}