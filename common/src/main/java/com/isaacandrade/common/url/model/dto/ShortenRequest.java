package com.isaacandrade.common.url.model.dto;

import java.io.Serializable;

public record ShortenRequest(
        String longUrl,
        String alias
) implements Serializable {
}
