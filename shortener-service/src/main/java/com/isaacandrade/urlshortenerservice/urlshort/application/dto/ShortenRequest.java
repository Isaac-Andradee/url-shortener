package com.isaacandrade.urlshortenerservice.urlshort.application.dto;

import java.io.Serializable;

public record ShortenRequest(
        String longUrl,
        String alias
) implements Serializable {
}
