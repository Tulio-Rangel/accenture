package com.tulio.accenture.domain.ports.in;

import com.tulio.accenture.shared.dto.BranchDto;
import com.tulio.accenture.shared.dto.FranchiseDto;
import com.tulio.accenture.shared.dto.ProductDto;
import com.tulio.accenture.shared.dto.TopProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseService {
    Mono<FranchiseDto> createFranchise(FranchiseDto franchiseDto);
    Mono<FranchiseDto> updateFranchiseName(String franchiseId, String newName);
    Mono<FranchiseDto> addBranch(String franchiseId, BranchDto branchDto);
    Mono<FranchiseDto> updateBranchName(String franchiseId, String branchId, String newName);
    Mono<FranchiseDto> addProduct(String franchiseId, String branchId, ProductDto productDto);
    Mono<FranchiseDto> removeProduct(String franchiseId, String branchId, String productId);
    Mono<FranchiseDto> updateProductStock(String franchiseId, String branchId, String productId, Integer newStock);
    Mono<FranchiseDto> updateProductName(String franchiseId, String branchId, String productId, String newName);
    Flux<TopProductDto> getTopProductsByBranch(String franchiseId);
    Mono<FranchiseDto> getFranchiseById(String franchiseId);
    Flux<FranchiseDto> getAllFranchises();
}
