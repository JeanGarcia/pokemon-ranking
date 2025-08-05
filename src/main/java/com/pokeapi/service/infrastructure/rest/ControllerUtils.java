package com.pokeapi.service.infrastructure.rest;

import com.pokeapi.service.infrastructure.rest.model.RankingRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * ControllerUtils
 *
 * @author Jean
 */
@Component
public class ControllerUtils {

    private static final String STAT_TYPE = "statType";
    private static final String OFFSET = "offset";
    private static final String LIMIT = "limit";

    public String buildRankingNextLink(RankingRequestDto request, int maxLimit, ServerWebExchange exchange) {
        int nextOffset = request.offset() + request.limit();
        if (nextOffset >= maxLimit) {
            return null; // There's no next page
        }
        return UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
                .replaceQueryParam(STAT_TYPE, request.statType())
                .replaceQueryParam(OFFSET, nextOffset)
                .replaceQueryParam(LIMIT, request.limit())
                .toUriString();
    }

    public String buildRankingPreviousLink(RankingRequestDto request, ServerWebExchange exchange) {
        int nextOffset = request.offset() - request.limit();
        if (nextOffset < 0) {
            return null; // There's no previous page
        }
        return UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
                .replaceQueryParam(STAT_TYPE, request.statType())
                .replaceQueryParam(OFFSET, nextOffset)
                .replaceQueryParam(LIMIT, request.limit())
                .toUriString();
    }
}
