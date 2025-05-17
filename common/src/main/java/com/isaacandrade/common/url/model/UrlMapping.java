package com.isaacandrade.common.url.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "urls")
public class UrlMapping implements Serializable {

    @Id
    private String shortKey;
    private String longUrl;
    private String alias;

    public UrlMapping() {}

    @PersistenceCreator
    public UrlMapping(String shortKey, String longUrl, String alias) {
        this.shortKey = shortKey;
        this.longUrl = longUrl;
        this.alias = alias;
    }

    public String getShortKey() {
        return shortKey;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getAlias() {return alias;}

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public void setLongUrl(String longUrl) {this.longUrl = longUrl;}

    public void setAlias(String alias) {this.alias = alias;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UrlMapping that = (UrlMapping) o;
        return Objects.equals(shortKey, that.shortKey) && Objects.equals(longUrl, that.longUrl) && Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortKey, longUrl, alias);
    }
}
