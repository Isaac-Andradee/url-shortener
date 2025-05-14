package com.isaacandrade.urlshortenerservice.url.application.dto;

public record ShortenRequest(
        String longUrl,
        String alias
) {
}
