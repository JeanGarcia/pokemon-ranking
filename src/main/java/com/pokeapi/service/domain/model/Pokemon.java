package com.pokeapi.service.domain.model;

import java.util.Objects;

/**
 * Pokemon
 *
 * @author Jean
 */

public class Pokemon {

    private int id;
    private String name;
    private int height;
    private int weight;
    private int baseExperience;
    private String spriteUrl;

    public Pokemon() {
    }

    public Pokemon(int id, String name, int height, int weight, int baseExperience, String spriteUrl) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
        this.spriteUrl = spriteUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && height == pokemon.height && weight == pokemon.weight && baseExperience == pokemon.baseExperience && Objects.equals(name, pokemon.name) && Objects.equals(spriteUrl, pokemon.spriteUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, height, weight, baseExperience, spriteUrl);
    }
}
