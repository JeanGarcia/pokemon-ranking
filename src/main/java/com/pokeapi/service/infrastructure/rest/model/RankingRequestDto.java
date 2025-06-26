package com.pokeapi.service.infrastructure.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

/**
 * RankingRequestDto
 *
 * @author Jean
 */
@Builder
@Data
public class RankingRequestDto {

        @JsonProperty("stat_type")
        @NotBlank(message = "Stat type cannot be blank")
        private String statType;

        @JsonProperty("offset")
        @Builder.Default
        @PositiveOrZero(message = "offset must be a positive number or zero")
        private int offset = 0;

        @JsonProperty("limit")
        @Builder.Default
        @Positive(message = "limit must be a positive number")
        private int limit = 20;

}
