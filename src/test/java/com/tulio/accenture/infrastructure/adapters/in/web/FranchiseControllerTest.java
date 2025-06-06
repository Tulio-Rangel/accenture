package com.tulio.accenture.infrastructure.adapters.in.web;

import com.tulio.accenture.domain.ports.in.FranchiseService;
import com.tulio.accenture.shared.dto.BranchDto;
import com.tulio.accenture.shared.dto.FranchiseDto;
import com.tulio.accenture.shared.dto.TopProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranchiseControllerTest {

    @Mock
    private FranchiseService franchiseService;

    @InjectMocks
    private FranchiseController franchiseController;

    @Test
    void createFranchiseReturnsCreatedResponseWhenValidInput() {
        FranchiseDto franchiseDto = new FranchiseDto();
        FranchiseDto createdFranchise = new FranchiseDto();
        when(franchiseService.createFranchise(franchiseDto)).thenReturn(Mono.just(createdFranchise));

        StepVerifier.create(franchiseController.createFranchise(franchiseDto))
                .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(createdFranchise))
                .verifyComplete();
    }

    @Test
    void getAllFranchisesReturnsFluxOfFranchises() {
        FranchiseDto franchise1 = new FranchiseDto();
        FranchiseDto franchise2 = new FranchiseDto();
        when(franchiseService.getAllFranchises()).thenReturn(Flux.just(franchise1, franchise2));

        StepVerifier.create(franchiseController.getAllFranchises())
                .expectNext(franchise1, franchise2)
                .verifyComplete();
    }

    @Test
    void getFranchiseByIdReturnsOkResponseWhenFranchiseExists() {
        String franchiseId = "123";
        FranchiseDto franchiseDto = new FranchiseDto();
        when(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.just(franchiseDto));

        StepVerifier.create(franchiseController.getFranchiseById(franchiseId))
                .expectNext(ResponseEntity.ok(franchiseDto))
                .verifyComplete();
    }

    @Test
    void getFranchiseByIdReturnsEmptyWhenFranchiseDoesNotExist() {
        String franchiseId = "123";
        when(franchiseService.getFranchiseById(franchiseId)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseController.getFranchiseById(franchiseId))
                .verifyComplete();
    }

    @Test
    void updateFranchiseNameReturnsUpdatedFranchiseWhenValidInput() {
        String franchiseId = "123";
        String newName = "New Franchise Name";
        FranchiseDto updatedFranchise = new FranchiseDto();
        when(franchiseService.updateFranchiseName(franchiseId, newName)).thenReturn(Mono.just(updatedFranchise));

        StepVerifier.create(franchiseController.updateFranchiseName(franchiseId, Map.of("name", newName)))
                .expectNext(ResponseEntity.ok(updatedFranchise))
                .verifyComplete();
    }

    @Test
    void addBranchReturnsCreatedResponseWhenValidInput() {
        String franchiseId = "123";
        BranchDto branchDto = new BranchDto();
        FranchiseDto updatedFranchise = new FranchiseDto();
        when(franchiseService.addBranch(franchiseId, branchDto)).thenReturn(Mono.just(updatedFranchise));

        StepVerifier.create(franchiseController.addBranch(franchiseId, branchDto))
                .expectNext(ResponseEntity.status(HttpStatus.CREATED).body(updatedFranchise))
                .verifyComplete();
    }

    @Test
    void removeProductReturnsOkResponseWhenProductRemoved() {
        String franchiseId = "123";
        String branchId = "456";
        String productId = "789";
        FranchiseDto updatedFranchise = new FranchiseDto();
        when(franchiseService.removeProduct(franchiseId, branchId, productId)).thenReturn(Mono.just(updatedFranchise));

        StepVerifier.create(franchiseController.removeProduct(franchiseId, branchId, productId))
                .expectNext(ResponseEntity.ok(updatedFranchise))
                .verifyComplete();
    }

    @Test
    void getTopProductsByBranchReturnsFluxOfTopProducts() {
        String franchiseId = "123";
        TopProductDto topProduct1 = new TopProductDto();
        TopProductDto topProduct2 = new TopProductDto();
        when(franchiseService.getTopProductsByBranch(franchiseId)).thenReturn(Flux.just(topProduct1, topProduct2));

        StepVerifier.create(franchiseController.getTopProductsByBranch(franchiseId))
                .expectNext(topProduct1, topProduct2)
                .verifyComplete();
    }
}
