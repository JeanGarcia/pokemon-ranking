package com.pokeapi.service.unit.infrastructure.rest;

import com.pokeapi.service.infrastructure.rest.ControllerUtils;
import com.pokeapi.service.infrastructure.rest.model.RankingRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ControllerUtilsTest
 *
 * @author Jean
 */
@SpringBootTest
@ActiveProfiles("test")
public class ControllerUtilsTest {

    @Autowired
    private ControllerUtils controllerUtils;

    @Test
    @DisplayName("should build ranking next link with valid parameters")
    void should_build_next_link() {
        RankingRequestDto request = new RankingRequestDto("height", 20, 10);
        String result = controllerUtils.buildRankingNextLink(request, 50);
        assertNotNull(result);
        assertTrue(result.contains("statType=height"));
        assertTrue(result.contains("offset=30"));
        assertTrue(result.contains("limit=10"));
    }

    @Test
    @DisplayName("should build ranking next link as null when next offset is greater than max limit")
    void should_get_next_link_as_null() {
        RankingRequestDto request = new RankingRequestDto("height", 50, 20);
        String result = controllerUtils.buildRankingNextLink(request, 60);
        assertNull(result);
    }

    @Test
    @DisplayName("should build ranking previous link with valid parameters")
    void should_build_previous_link() {
        RankingRequestDto request = new RankingRequestDto("height", 30, 10);
        String result = controllerUtils.buildRankingPreviousLink(request);
        assertNotNull(result);
        assertTrue(result.contains("statType=height"));
        assertTrue(result.contains("offset=20"));
        assertTrue(result.contains("limit=10"));
    }

    @Test
    @DisplayName("should build ranking previous link as null when next offset is less than zero")
    void should_get_previous_link_as_null() {
        RankingRequestDto request = new RankingRequestDto("height", 10, 20);
        String result = controllerUtils.buildRankingPreviousLink(request);
        assertNull(result);
    }

}
