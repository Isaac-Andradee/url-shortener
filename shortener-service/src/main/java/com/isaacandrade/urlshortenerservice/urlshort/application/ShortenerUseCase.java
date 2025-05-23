package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.urlshortenerservice.config.ShortenerProperties;
import com.isaacandrade.common.url.model.dto.ShortenResponse;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import com.isaacandrade.common.url.model.dto.ShortenRequest;
import org.springframework.stereotype.Service;


@Service
public class ShortenerUseCase {

    private final ShortenerProperties domainProperties;
    private final AliasValidationComposite aliasValidation;
    private final KeyGenResolver keyGenResolver;
    private final DbCacheSaver dbCacheSaver;

    public ShortenerUseCase(ShortenerProperties domainProperties, AliasValidationComposite aliasValidation, KeyGenResolver keyGenResolver, DbCacheSaver dbCacheSaver) {
        this.domainProperties = domainProperties;
        this.aliasValidation = aliasValidation;
        this.keyGenResolver = keyGenResolver;
        this.dbCacheSaver = dbCacheSaver;
    }

    public ShortenResponse shorten(ShortenRequest request) {
        validateAlias(request.alias());
        String shortKey = generateShortKey(request.alias());
        UrlMapping urlMapping = buildMapping(shortKey, request);
        persist(urlMapping);
        return buildShortUrl(shortKey);
    }

    private void validateAlias(String alias) {
        aliasValidation.validate(alias);
    }

    private String generateShortKey(String alias) {
        return keyGenResolver.resolveShortKey(alias);
    }

    private UrlMapping buildMapping(String shortKey, ShortenRequest request) {
        return new UrlMapping(shortKey, request.longUrl(), request.alias());
    }

    private void persist(UrlMapping urlMapping) {
        dbCacheSaver.saveUrlMapping(urlMapping);
    }

    private ShortenResponse buildShortUrl(String shortKey) {
        return new ShortenResponse( domainProperties + shortKey);
    }
}
