package com.isaacandrade.urlshortenerservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Shortener Service Doc")
                        .description("Shortener Service API")
                        .version("v1.0.0")
                        .termsOfService("/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/Isaac-Andradee/url-shortener/blob/main/LICENSE")
                        )
                );
    }
}
