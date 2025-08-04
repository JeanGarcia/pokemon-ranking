package com.pokeapi.service.domain.exception;

public class PokemonSyncFailedException extends RuntimeException {
    public PokemonSyncFailedException(String message) {
        super(message);
    }

    public PokemonSyncFailedException(String message, Exception e) {
        super(message, e);
    }
}
