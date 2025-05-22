package com.isaacandrade.urlshortenerservice.integration_tests.urlshort.application.config;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest
public abstract class BaseEurekaServerTest {
    static GenericContainer<?> eurekaServer =
            new GenericContainer<>("isaacandra/eureka:latest")
                    .withExposedPorts(8761);

    @BeforeAll
    static void startEureka() {
        eurekaServer.start();
        System.setProperty(
                "eureka.client.serviceUrl.defaultZone",
                "http://" +
                        eurekaServer.getHost() + ":" +
                        eurekaServer.getMappedPort(8761) +
                        "/eureka/"
        );
    }
}
