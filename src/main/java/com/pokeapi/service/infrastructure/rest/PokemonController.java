package com.pokeapi.service.infrastructure.rest;

import com.pokeapi.service.domain.model.Pokemon;
import com.pokeapi.service.domain.service.RankingService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
public class PokemonController {

    private static final String STAT_TYPE = "statType";
    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";
    private final RankingService rankingService;
    private final PokemonMapper pokemonMapper;

    @Operation(
            summary = "It retrieves a ranking of Pokémon based on a specified stat type.",
            description = "The stat type can be weight, height, or base experience. The ranking is paginated with offset and limit parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RankingResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = {@Content(mediaType = "application/json")})
    })
    @GetMapping(value = "/ranking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankingResponseDto> getRanking(@Valid @ParameterObject @ModelAttribute RankingRequestDto request) {
        try {
            log.debug("[GET-RANKING] request received: {}", request);

            List<Pokemon> pokemonList = rankingService.getAllPokemon();
            int pokemonCount = pokemonList.size();
            RankingResponseDto response = pokemonMapper.toRankingResponseDto(
                    rankingService.rankPokemonListByStat(
                            pokemonList,
                            request.getStatType(),
                            request.getOffset(),
                            request.getLimit()),
                    pokemonCount,
                    buildNextLink(request),
                    buildPreviousLink(request)
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error getting ranking", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private String buildNextLink(RankingRequestDto request) {
        int nextOffset = request.getOffset() + request.getLimit();
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replaceQueryParam(STAT_TYPE, request.getStatType())
                .replaceQueryParam(OFFSET, nextOffset)
                .replaceQueryParam(LIMIT, request.getLimit())
                .toUriString();
    }

    private String buildPreviousLink(RankingRequestDto request) {
        int nextOffset = request.getOffset() - request.getLimit();
        if (nextOffset < 0) {
            return null; // There's no previous page
        }
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .replaceQueryParam(STAT_TYPE, request.getStatType())
                .replaceQueryParam(OFFSET, nextOffset)
                .replaceQueryParam(LIMIT, request.getLimit())
                .toUriString();
    }

}
