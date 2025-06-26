package com.pokeapi.service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class CacheConfiguration {

    @Value("${application.cache.fixed-delay:31104000000}")
    private Long fixedDelay;

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
    @Scheduled(fixedDelayString = "${application.cache.fixed-delay:31104000000}")
    public void clearCache() {
        log.info("Pokemon list cache cleared successfully, next clear in ms {} ms", fixedDelay);
    }

}
