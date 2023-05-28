package com.codingchallenge.coinrate.currencyservice.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The caffeine-based Springframework cache configuration.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * The caffeine-based cache manager.
     *
     * @return The cache manager.
     */
    @Bean
    public CaffeineCacheManager cacheManager() {

        return new CaffeineCacheManager();
    }
}
