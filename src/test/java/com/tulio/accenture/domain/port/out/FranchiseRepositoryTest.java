package com.tulio.accenture.domain.port.out;

import com.tulio.accenture.domain.entities.Franchise;
import com.tulio.accenture.domain.ports.out.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseRepositoryTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Test
    void saveShouldReturnSavedFranchise() {
        Franchise franchise = new Franchise("Franchise Name");
        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseRepository.save(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void findByIdShouldReturnFranchiseWhenExists() {
        Franchise franchise = new Franchise("Franchise Name");
        when(franchiseRepository.findById("1")).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseRepository.findById("1"))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void findByIdShouldReturnEmptyWhenNotExists() {
        when(franchiseRepository.findById("2")).thenReturn(Mono.empty());

        StepVerifier.create(franchiseRepository.findById("2"))
                .verifyComplete();
    }

    @Test
    void findAllShouldReturnAllFranchises() {
        Franchise franchise1 = new Franchise("Franchise One");
        Franchise franchise2 = new Franchise("Franchise Two");
        when(franchiseRepository.findAll()).thenReturn(Flux.just(franchise1, franchise2));

        StepVerifier.create(franchiseRepository.findAll())
                .expectNext(franchise1, franchise2)
                .verifyComplete();
    }

    @Test
    void deleteByIdShouldCompleteWithoutErrors() {
        when(franchiseRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(franchiseRepository.deleteById("1"))
                .verifyComplete();
    }

    @Test
    void existsByIdShouldReturnTrueWhenExists() {
        when(franchiseRepository.existsById("1")).thenReturn(Mono.just(true));

        StepVerifier.create(franchiseRepository.existsById("1"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsByIdShouldReturnFalseWhenNotExists() {
        when(franchiseRepository.existsById("2")).thenReturn(Mono.just(false));

        StepVerifier.create(franchiseRepository.existsById("2"))
                .expectNext(false)
                .verifyComplete();
    }
}
