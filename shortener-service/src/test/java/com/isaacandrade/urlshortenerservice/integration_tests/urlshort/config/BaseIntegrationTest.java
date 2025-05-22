package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.config;

import com.isaacandrade.urlshortenerservice.utils.EnvUtil;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@Testcontainers
@SpringBootTest
public abstract class BaseIntegrationTest {
    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0");


    @DynamicPropertySource
    static void props(DynamicPropertyRegistry reg) {
        reg.add("spring.redis.cluster.nodes", List::of);
        reg.add("spring.data.mongodb.uri", mongo::getConnectionString);
    }
}