package com.pokeapi.service.domain.exception;

public class NoPokemonException extends RuntimeException {
    public NoPokemonException(String message) {
        super(message);
    }
}
