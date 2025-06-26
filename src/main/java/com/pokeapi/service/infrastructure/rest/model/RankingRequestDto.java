package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * RankingRequestDto
 *
 * @author Jean
 */
public record RankingRequestDto(
        @JsonProperty("stat_type")
        @NotBlank(message = "Stat type cannot be blank")
        String statType,

        @JsonProperty("offset")
        @PositiveOrZero(message = "offset must be a positive number or zero")
        Integer offset,

        @JsonProperty("limit")
        @Positive(message = "limit must be a positive number")
        Integer limit
) {
    public RankingRequestDto {
        // Default values for offset and limit
        if (offset == null) {
            offset = 0;
        }
        if (limit == null) {
            limit = 5;
        }
    }
}
