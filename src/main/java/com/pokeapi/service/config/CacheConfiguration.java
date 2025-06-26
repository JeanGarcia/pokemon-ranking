package com.pokeapi.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * CacheConfiguration
 *
 * @author Jean
 */
@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
@ConfigurationProperties(prefix = "pokeapi.max-limit")
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager manager = new ConcurrentMapCacheManager();
        manager.setAllowNullValues(false);
        manager.setCacheNames(List.of(
                "pokemonList"
        ));
        return manager;
    }

    @CacheEvict(value = "pokemonList", allEntries = true)
    @Scheduled(fixedDelay = 604800000)
    public void clearCache() {
        // This method will be called every week to clear the cache
        log.info("Pokemon list cleared successfully");
    }

}
