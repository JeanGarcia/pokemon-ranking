package com.pokeapi.service.infrastructure.rest.config;

import com.pokeapi.service.domain.exception.FailToRetrievePokemonException;
import com.pokeapi.service.domain.exception.InvalidArgumentException;
import com.pokeapi.service.domain.exception.NoPokemonException;
import com.pokeapi.service.domain.exception.PokemonSyncFailedException;
import com.pokeapi.service.infrastructure.rest.model.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

/**
 * RestErrorConfiguration
 *
 * @author Jean
 */
@RestControllerAdvice
public class RestErrorConfiguration {

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(Exception ex, HttpStatus status, HttpServletRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                Instant.now().toString(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({NoPokemonException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFound(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({InvalidArgumentException.class})
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({
            Exception.class,
            RuntimeException.class,
            FailToRetrievePokemonException.class,
            PokemonSyncFailedException.class
    })
    public ResponseEntity<ErrorResponseDto> handleInternalError(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
