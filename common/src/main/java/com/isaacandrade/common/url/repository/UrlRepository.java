package com.isaacandrade.common.url.repository;

import com.isaacandrade.common.url.model.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<UrlMapping, String> {
}
