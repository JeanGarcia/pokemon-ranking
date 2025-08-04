package com.pokeapi.service.infrastructure.rest;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.PokemonRanker;
import com.pokeapi.service.infrastructure.rest.model.RankingRequestDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RankingController
 *
 * @author Jean
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/pokemon")
public class PokemonRankingController {

    private final ControllerUtils controllerUtils;
    private final PokemonRanker pokemonRanker;
    private final PokemonRankingMapper pokemonRankingMapper;


    @Operation(
            summary = "It retrieves a ranking of Pokémon based on a specified stat type.",
            description = "The stat type can be weight, height, or base_experience. The ranking is paginated with offset and limit parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RankingResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/ranking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankingResponseDto> getRanking(@Valid @ParameterObject @ModelAttribute RankingRequestDto request) {
        log.info("[GET-RANKING] request received: {}", request);

        final List<Pokemon> pokemonList = pokemonRanker.getAllPokemon();
        final int pokemonCount = pokemonList.size();

        RankingResponseDto response = pokemonRankingMapper.toRankingResponseDto(
                pokemonRanker.rankPokemonListByStat(
                        pokemonList,
                        request.statType(),
                        request.offset(),
                        request.limit()),
                pokemonCount,
                controllerUtils.buildRankingNextLink(request, pokemonCount),
                controllerUtils.buildRankingPreviousLink(request)
        );

        return ResponseEntity.ok(response);
    }

}
