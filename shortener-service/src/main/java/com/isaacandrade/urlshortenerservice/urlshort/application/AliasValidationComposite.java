package com.isaacandrade.urlshortenerservice.urlshort.application;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AliasValidationComposite {
    private final List<AliasValidator> validators;

    public AliasValidationComposite(List<AliasValidator> validators) {
        this.validators = validators;
    }

    public void validate(String alias) {
        for (AliasValidator validator : validators) {
            validator.validate(alias);
        }
    }
}
