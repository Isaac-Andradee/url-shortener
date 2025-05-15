package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class AliasValidator {
    private final UrlRepository urlRepository;

    public AliasValidator(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void validateIfExistInDb(String alias) {
        if(!Strings.isBlank(alias) && urlRepository.findById(alias).isPresent()) {
            throw new AliasNotAvailableException();
        }
    }

    public boolean isAliasOnRequest(String alias) {
        return !Strings.isBlank(alias);
    }
}
