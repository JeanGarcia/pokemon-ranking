package com.pokeapi.service.infrastructure.rest;

import com.pokeapi.service.domain.service.PokemonSyncer;
import com.pokeapi.service.infrastructure.rest.model.PokemonSyncRequestDto;
import com.pokeapi.service.infrastructure.rest.model.PokemonSyncResponseDto;
import com.pokeapi.service.infrastructure.rest.model.RankingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * PokemonSyncingController
 *
 * @author Jean
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sync")
public class PokemonSyncingController {
    private final PokemonSyncer pokemonLoader;


    @Operation(
            summary = "It syncs the Pokémon list from to original source (Pokeapi) with the system",
            description = "It syncs the Pokémon list from to original source (Pokeapi) with the system." +
                    "To avoid several calls to Pokeapi, it will only sync if it has not been synced before."
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
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonSyncResponseDto> syncAll() {
        log.info("[SYNCING POKEMON LIST]");
        final String targetEndpoint = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .toUriString() + "/notification-handler";
        pokemonLoader.syncAllPokemon(targetEndpoint);
        return ResponseEntity.ok(new PokemonSyncResponseDto("Pokemon list is being synced", null));
    }

    @Operation(
            summary = "It syncs a single Pokémon by ID from to original source (Pokeapi) with the system",
            description = "It syncs a single Pokémon by ID from to original source (Pokeapi) with the system." +
                    "To avoid several calls to Pokeapi, it will only sync if it has not been synced before."
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
    @PostMapping(value = "/notification-handler", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PokemonSyncResponseDto> syncNotification(@RequestBody PokemonSyncRequestDto request) {
        log.info("[SYNCING POKEMON WITH ID: {}]", request.pokemonId());
        pokemonLoader.syncPokemonById(request.pokemonId());
        return ResponseEntity.ok(new PokemonSyncResponseDto("Pokémon" + request.pokemonId() + " synced", null));
    }

}
