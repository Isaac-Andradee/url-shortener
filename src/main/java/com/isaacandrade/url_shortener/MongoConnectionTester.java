package com.isaacandrade.url_shortener;

import com.isaacandrade.url_shortener.domain.TestEntity;
import com.isaacandrade.url_shortener.repository.TestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionTester implements CommandLineRunner {

    private final TestRepository repository;

    public MongoConnectionTester(TestRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        TestEntity entity = new TestEntity(null, "MongoDB is working!");
//        repository.save(entity);
        System.out.println("✔️ Test document saved: " + entity);
    }
}