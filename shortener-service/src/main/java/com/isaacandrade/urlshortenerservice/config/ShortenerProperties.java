package com.isaacandrade.urlshortenerservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "shortener")
public class ShortenerProperties {
    private String domain;
    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    @Override
    public String toString() {
        return  domain;
    }
}
