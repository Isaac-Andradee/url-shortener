package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.common.url.model.UrlMapping;
import com.isaacandrade.urlshortenerservice.config.ShortenerProperties;
import com.isaacandrade.urlshortenerservice.urlshort.infra.DB.DbCacheSaver;
import com.isaacandrade.urlshortenerservice.urlshort.application.dto.ShortenRequest;
import org.springframework.stereotype.Service;


@Service
public class ShortenerUseCase {

    private final ShortenerProperties properties;
    private final AliasValidator aliasValidator;
    private final KeyGenResolver keyGenResolver;
    private final DbCacheSaver dbCacheSaver;

    public ShortenerUseCase(ShortenerProperties properties, AliasValidator aliasValidator, KeyGenResolver keyGenResolver, DbCacheSaver dbCacheSaver) {
        this.properties = properties;
        this.aliasValidator = aliasValidator;
        this.keyGenResolver = keyGenResolver;
        this.dbCacheSaver = dbCacheSaver;
    }

    public String shorten(ShortenRequest request) {
        String alias = request.alias();
        aliasValidator.validateIfExistInDb(alias);
        String shortKey = keyGenResolver.resolveShortKey(alias);
        UrlMapping urlMapping = new UrlMapping(shortKey, request.longUrl(), request.alias());
        dbCacheSaver.saveUrlMapping(urlMapping);
        return properties + "/" + shortKey;
    }
}
