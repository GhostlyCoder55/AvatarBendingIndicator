package com.avatarbendingindicator.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CHARACTERS = "characters";
    public static final String CHARACTER_LOOKUPS = "characterLookups";
    public static final String NATIONS = "nations";
    public static final String BENDING_STYLES = "bendingStyles";
    public static final String AFFILIATIONS = "affiliations";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(Duration.ofMinutes(10)));
        cacheManager.setCacheNames(List.of(
                CHARACTERS, CHARACTER_LOOKUPS, NATIONS, BENDING_STYLES, AFFILIATIONS));
        return cacheManager;
    }
}
