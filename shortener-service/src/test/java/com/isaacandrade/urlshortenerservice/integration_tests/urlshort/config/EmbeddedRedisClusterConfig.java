package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.config;


import com.github.shiyouping.redis.embedded.RedisCluster;
import com.github.shiyouping.redis.embedded.config.Config;
import com.github.shiyouping.redis.embedded.config.ConfigBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class EmbeddedRedisClusterConfig {

    private RedisCluster redisCluster;

    @PostConstruct
    public void startCluster() {
        Config config = new ConfigBuilder()
                .port(16379)
                .clusterNodeTimeout(5000)
                .build();
        redisCluster = new RedisCluster(config);
        redisCluster.start();
    }

    @PreDestroy
    public void stopCluster() {
        if (redisCluster != null) {
            redisCluster.stop();
        }
    }
}
