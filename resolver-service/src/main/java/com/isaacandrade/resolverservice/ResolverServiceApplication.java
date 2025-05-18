package com.isaacandrade.resolverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.isaacandrade.common", "com.isaacandrade.resolverservice"})
@EnableMongoRepositories(basePackages = "com.isaacandrade.common.url.repository")
public class ResolverServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResolverServiceApplication.class, args);
	}

}
