package com.tulio.accenture.infrastructure.adapters.in.web;

import com.tulio.accenture.domain.ports.in.FranchiseService;
import com.tulio.accenture.shared.dto.BranchDto;
import com.tulio.accenture.shared.dto.FranchiseDto;
import com.tulio.accenture.shared.dto.ProductDto;
import com.tulio.accenture.shared.dto.TopProductDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/franchises")
@Validated
@CrossOrigin(origins = "*")
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @PostMapping
    public Mono<ResponseEntity<FranchiseDto>> createFranchise(@Valid @RequestBody FranchiseDto franchiseDto) {
        return franchiseService.createFranchise(franchiseDto)
                .map(franchise -> ResponseEntity.status(HttpStatus.CREATED).body(franchise));
    }

    @GetMapping
    public Flux<FranchiseDto> getAllFranchises() {
        return franchiseService.getAllFranchises();
    }

    @GetMapping("/{franchiseId}")
    public Mono<ResponseEntity<FranchiseDto>> getFranchiseById(@PathVariable String franchiseId) {
        return franchiseService.getFranchiseById(franchiseId)
                .map(franchise -> ResponseEntity.ok().body(franchise));
    }

    @PutMapping("/{franchiseId}/name")
    public Mono<ResponseEntity<FranchiseDto>> updateFranchiseName(
            @PathVariable String franchiseId,
            @RequestBody Map<String, String> nameUpdate) {
        return franchiseService.updateFranchiseName(franchiseId, nameUpdate.get("name"))
                .map(franchise -> ResponseEntity.ok().body(franchise));
    }

    @PostMapping("/{franchiseId}/branches")
    public Mono<ResponseEntity<FranchiseDto>> addBranch(
            @PathVariable String franchiseId,
            @Valid @RequestBody BranchDto branchDto) {
        return franchiseService.addBranch(franchiseId, branchDto)
                .map(franchise -> ResponseEntity.status(HttpStatus.CREATED).body(franchise));
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/name")
    public Mono<ResponseEntity<FranchiseDto>> updateBranchName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @RequestBody Map<String, String> nameUpdate) {
        return franchiseService.updateBranchName(franchiseId, branchId, nameUpdate.get("name"))
                .map(franchise -> ResponseEntity.ok().body(franchise));
    }

    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    public Mono<ResponseEntity<FranchiseDto>> addProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @Valid @RequestBody ProductDto productDto) {
        return franchiseService.addProduct(franchiseId, branchId, productDto)
                .map(franchise -> ResponseEntity.status(HttpStatus.CREATED).body(franchise));
    }

    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<ResponseEntity<FranchiseDto>> removeProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId) {
        return franchiseService.removeProduct(franchiseId, branchId, productId)
                .map(franchise -> ResponseEntity.ok().body(franchise));
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<ResponseEntity<FranchiseDto>> updateProductStock(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody Map<String, Integer> stockUpdate) {
        return franchiseService.updateProductStock(franchiseId, branchId, productId, stockUpdate.get("stock"))
                .map(franchise -> ResponseEntity.ok().body(franchise));
    }

    @PutMapping("/{franchiseId}/branches/{branchId}/products/{productId}/name")
    public Mono<ResponseEntity<FranchiseDto>> updateProductName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody Map<String, String> nameUpdate) {
        return franchiseService.updateProductName(franchiseId, branchId, productId, nameUpdate.get("name"))
                .map(franchise -> ResponseEntity.ok().body(franchise));
    }

    @GetMapping("/{franchiseId}/top-products")
    public Flux<TopProductDto> getTopProductsByBranch(@PathVariable String franchiseId) {
        return franchiseService.getTopProductsByBranch(franchiseId);
    }
}
