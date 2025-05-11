package com.isaacandrade.url_shortener;

import com.isaacandrade.url_shortener.service.CacheService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RedisTestRunner implements CommandLineRunner {

    private final CacheService cacheService;

    public RedisTestRunner(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void run(String... args) {
        cacheService.save("abc123", "https://www.google.com");
        System.out.println("URL Recuperada: " + cacheService.get("abc123"));
    }
}
