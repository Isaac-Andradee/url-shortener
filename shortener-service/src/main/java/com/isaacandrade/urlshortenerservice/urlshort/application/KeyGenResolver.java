package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class KeyGenResolver {
    private final KeyGenClient keyGenClient;

    public KeyGenResolver(KeyGenClient keyGenClient) {
        this.keyGenClient = keyGenClient;
    }
    public String resolveShortKey(String alias) {
        return StringUtils.hasText(alias) ? alias : keyGenClient.generateKey();
    }
}
