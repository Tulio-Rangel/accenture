package com.tulio.accenture.application.services;

import com.tulio.accenture.domain.entities.Branch;
import com.tulio.accenture.domain.entities.Franchise;
import com.tulio.accenture.domain.entities.Product;
import com.tulio.accenture.domain.exceptions.EntityNotFoundException;
import com.tulio.accenture.domain.exceptions.InvalidDataException;
import com.tulio.accenture.domain.ports.out.FranchiseRepository;
import com.tulio.accenture.shared.dto.BranchDto;
import com.tulio.accenture.shared.dto.FranchiseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceImplTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseServiceImpl franchiseService;

    @Test
    void createFranchise_createsFranchiseSuccessfully() {
        FranchiseDto franchiseDto = new FranchiseDto();
        franchiseDto.setName("New Franchise");

        Franchise franchise = new Franchise("New Franchise");
        franchise.setId("123");

        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.createFranchise(franchiseDto))
                .expectNextMatches(result -> result.getId().equals("123") && result.getName().equals("New Franchise"))
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_updatesNameSuccessfully() {
        Franchise franchise = new Franchise("Old Name");
        franchise.setId("123");

        when(franchiseRepository.findById("123")).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.updateFranchiseName("123", "New Name"))
                .expectNextMatches(result -> result.getName().equals("New Name"))
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_throwsEntityNotFoundExceptionWhenFranchiseNotFound() {
        when(franchiseRepository.findById("123")).thenReturn(Mono.empty());

        StepVerifier.create(franchiseService.updateFranchiseName("123", "New Name"))
                .expectErrorMatches(throwable -> throwable instanceof EntityNotFoundException &&
                        throwable.getMessage().equals("Franchise not found with id: 123"))
                .verify();
    }

    @Test
    void addBranch_addsBranchSuccessfully() {
        Franchise franchise = new Franchise("Franchise");
        franchise.setId("123");

        BranchDto branchDto = new BranchDto();
        branchDto.setName("New Branch");

        when(franchiseRepository.findById("123")).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.addBranch("123", branchDto))
                .expectNextMatches(result -> result.getBranches().stream()
                        .anyMatch(branch -> branch.getName().equals("New Branch")))
                .verifyComplete();
    }

    @Test
    void addBranch_throwsEntityNotFoundExceptionWhenFranchiseNotFound() {
        BranchDto branchDto = new BranchDto();
        branchDto.setName("New Branch");

        when(franchiseRepository.findById("123")).thenReturn(Mono.empty());

        StepVerifier.create(franchiseService.addBranch("123", branchDto))
                .expectErrorMatches(throwable -> throwable instanceof EntityNotFoundException &&
                        throwable.getMessage().equals("Franchise not found with id: 123"))
                .verify();
    }

    @Test
    void updateProductStock_updatesStockSuccessfully() {
        Franchise franchise = new Franchise("Franchise");
        franchise.setId("123");

        Branch branch = new Branch("Branch");
        branch.setId("456");

        Product product = new Product("Product", 10);
        product.setId("789");

        branch.addProduct(product);
        franchise.addBranch(branch);

        when(franchiseRepository.findById("123")).thenReturn(Mono.just(franchise));
        when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.updateProductStock("123", "456", "789", 20))
                .expectNextMatches(result -> result.getBranches().stream()
                        .flatMap(b -> b.getProducts().stream())
                        .anyMatch(p -> p.getStock() == 20))
                .verifyComplete();
    }

    @Test
    void updateProductStock_throwsInvalidDataExceptionWhenStockIsNegative() {
        Franchise franchise = new Franchise("Franchise");
        franchise.setId("123");

        Branch branch = new Branch("Branch");
        branch.setId("456");

        Product product = new Product("Product", 10);
        product.setId("789");

        branch.addProduct(product);
        franchise.addBranch(branch);

        when(franchiseRepository.findById("123")).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseService.updateProductStock("123", "456", "789", -5))
                .expectErrorMatches(throwable -> throwable instanceof InvalidDataException &&
                        throwable.getMessage().equals("Stock cannot be negative"))
                .verify();
    }
}
