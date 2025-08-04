package com.pokeapi.service.domain.exception;

public class FailToRetrievePokemonException extends RuntimeException {
    public FailToRetrievePokemonException(String message, Exception e) {
        super(message, e);
    }
}
