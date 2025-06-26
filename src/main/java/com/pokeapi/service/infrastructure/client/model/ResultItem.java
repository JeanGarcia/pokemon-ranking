package com.pokeapi.service.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ResultItem
 *
 * @author Jean
 */
@Data
public class ResultItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

}
