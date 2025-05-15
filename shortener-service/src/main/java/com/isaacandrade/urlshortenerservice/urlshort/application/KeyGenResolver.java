package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.urlshortenerservice.web.keygen.KeyGenClient;
import org.springframework.stereotype.Component;

@Component
public class KeyGenResolver {
    private final AliasValidator aliasValidator;
    private final KeyGenClient keyGenClient;

    public KeyGenResolver(AliasValidator aliasValidator, KeyGenClient keyGenClient) {
        this.aliasValidator = aliasValidator;
        this.keyGenClient = keyGenClient;
    }
    public String resolveShortKey(String alias) {
        return aliasValidator.isAliasOnRequest(alias) ? alias : keyGenClient.generateKey();
    }
}
