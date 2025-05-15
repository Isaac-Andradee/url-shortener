package com.isaacandrade.urlshortenerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.isaacandrade.common", "com.isaacandrade.urlshortenerservice"})
@EnableMongoRepositories(basePackages = "com.isaacandrade.common.url.repository")
public class UrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}

}
