package com.isaacandrade.common.url.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "urls")
public class UrlMapping implements Serializable {

    @Id
    private String shortKey;
    private String longUrl;

    public UrlMapping(String shortKey, String longUrl) {
        this.shortKey = shortKey;
        this.longUrl = longUrl;
    }

    public String getShortKey() {
        return shortKey;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UrlMapping that = (UrlMapping) o;
        return Objects.equals(shortKey, that.shortKey) && Objects.equals(longUrl, that.longUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortKey, longUrl);
    }
}
