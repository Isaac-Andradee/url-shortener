/*
 * Copyright 2025 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isaacandrade.common.url.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a URL mapping in the system.
 * This class is used to store the mapping between a short key and a long URL,
 * along with an optional alias for the URL.
 *
 * @author Isaac Andrade
 */
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
