package com.pokeapi.service.infrastructure.rest.config;

import com.pokeapi.service.domain.exception.FailToRetrievePokemonException;
import com.pokeapi.service.domain.exception.InvalidArgumentException;
import com.pokeapi.service.domain.exception.NoPokemonException;
import com.pokeapi.service.domain.exception.PokemonSyncFailedException;
import com.pokeapi.service.infrastructure.rest.model.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;

import java.time.Instant;

/**
 * RestErrorConfiguration
 *
 * @author Jean
 */
@RestControllerAdvice
public class RestErrorConfiguration {

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(Exception ex, HttpStatus status, ServerWebExchange exchange) {
        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({NoPokemonException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(Exception ex, ServerWebExchange exchange) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, exchange);
    }

    @ExceptionHandler({InvalidArgumentException.class})
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception ex, ServerWebExchange exchange) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, exchange);
    }

    @ExceptionHandler({
            Exception.class,
            RuntimeException.class,
            FailToRetrievePokemonException.class,
            PokemonSyncFailedException.class
    })
    public ResponseEntity<ErrorResponseDto> handleInternalError(Exception ex, ServerWebExchange exchange) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, exchange);
    }

}
