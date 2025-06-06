package com.tulio.accenture.domain.ports.out;

import com.tulio.accenture.domain.entities.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
    Flux<Franchise> findAll();
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}

