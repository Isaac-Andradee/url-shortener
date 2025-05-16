package com.isaacandrade.urlshortenerservice.urlshort.application.dto;

import java.io.Serializable;

public record ShortenResponse(String shortUrl) implements Serializable {
}
