package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.common.url.repository.UrlRepository;
import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasNotAvailableException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class AliasAvailabilityValidator implements AliasValidator{

    private final UrlRepository urlRepository;

    public AliasAvailabilityValidator(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public void validate(String alias) {
        if(!Strings.isBlank(alias) && urlRepository.findById(alias).isPresent())
            throw new AliasNotAvailableException("Alias " + alias +" is not available");
    }
}
