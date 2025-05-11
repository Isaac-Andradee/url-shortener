package com.isaacandrade.url_shortener.repository;

import com.isaacandrade.url_shortener.domain.TestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestEntity, String> {
}