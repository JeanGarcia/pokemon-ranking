package com.pokeapi.service.infrastructure.rest.model;

/**
 * ErrorResponse
 *
 * @author Jean
 */
public record ErrorResponseDto(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
