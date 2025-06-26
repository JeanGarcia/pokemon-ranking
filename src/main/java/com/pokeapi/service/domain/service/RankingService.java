package com.pokeapi.service.domain.service;


import com.pokeapi.service.domain.model.Pokemon;

import java.util.List;

/**
 * rankingService
 *
 * @author Jean
 */
public interface RankingService {

    List<Pokemon> rankPokemonListByStat(List<Pokemon> pokemonList, String statType, int offset, int limit);

    List<Pokemon> getAllPokemon();

}
