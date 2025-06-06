package com.tulio.accenture.infrastructure.adapters.out.persistence;

import com.tulio.accenture.domain.entities.Franchise;
import com.tulio.accenture.domain.ports.out.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryImpl implements FranchiseRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public FranchiseRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return mongoTemplate.save(franchise);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return mongoTemplate.findById(id, Franchise.class);
    }

    @Override
    public Flux<Franchise> findAll() {
        return mongoTemplate.findAll(Franchise.class);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, Franchise.class).then();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, Franchise.class);
    }
}
