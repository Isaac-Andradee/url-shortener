package com.isaacandrade.urlshortenerservice.urlshort.application;

import com.isaacandrade.urlshortenerservice.urlshort.exception.AliasInvalidFormatException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AliasFormatValidator implements AliasValidator{
    private static final Pattern PATTERN =  Pattern.compile("^[A-Za-z0-9](?!.*[_-]{2})[A-Za-z0-9_-]{1,18}[A-Za-z0-9]$");

    @Override
    public void validate(String alias) {
        if(!Strings.isBlank(alias) && !PATTERN.matcher(alias).matches())
            throw new AliasInvalidFormatException("Invalid Alias Format");
    }
}