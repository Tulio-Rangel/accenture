package com.tulio.accenture.application.services;

import com.tulio.accenture.domain.entities.Branch;
import com.tulio.accenture.domain.entities.Franchise;
import com.tulio.accenture.domain.entities.Product;
import com.tulio.accenture.domain.exceptions.EntityNotFoundException;
import com.tulio.accenture.domain.exceptions.InvalidDataException;
import com.tulio.accenture.domain.ports.in.FranchiseService;
import com.tulio.accenture.domain.ports.out.FranchiseRepository;
import com.tulio.accenture.shared.dto.BranchDto;
import com.tulio.accenture.shared.dto.FranchiseDto;
import com.tulio.accenture.shared.dto.ProductDto;
import com.tulio.accenture.shared.dto.TopProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseRepository franchiseRepository;

    @Autowired
    public FranchiseServiceImpl(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    @Override
    public Mono<FranchiseDto> createFranchise(FranchiseDto franchiseDto) {
        return Mono.fromCallable(() -> {
                    Franchise franchise = new Franchise(franchiseDto.getName());
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> updateFranchiseName(String franchiseId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    franchise.setName(newName);
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> addBranch(String franchiseId, BranchDto branchDto) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    Branch branch = new Branch(branchDto.getName());
                    branch.setId(UUID.randomUUID().toString());
                    franchise.addBranch(branch);
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> updateBranchName(String franchiseId, String branchId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));
                    branch.setName(newName);
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> addProduct(String franchiseId, String branchId, ProductDto productDto) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

                    Product product = new Product(productDto.getName(), productDto.getStock());
                    product.setId(UUID.randomUUID().toString());
                    branch.addProduct(product);
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> removeProduct(String franchiseId, String branchId, String productId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

                    boolean removed = branch.removeProduct(productId);
                    if (!removed) {
                        throw new EntityNotFoundException("Product not found with id: " + productId);
                    }
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> updateProductStock(String franchiseId, String branchId, String productId, Integer newStock) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

                    if (newStock < 0) {
                        throw new InvalidDataException("Stock cannot be negative");
                    }

                    product.setStock(newStock);
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Mono<FranchiseDto> updateProductName(String franchiseId, String branchId, String productId, String newName) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + branchId));

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

                    product.setName(newName);
                    return franchise;
                })
                .flatMap(franchiseRepository::save)
                .map(this::mapToDto);
    }

    @Override
    public Flux<TopProductDto> getTopProductsByBranch(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMapMany(franchise ->
                        Flux.fromIterable(franchise.getBranches())
                                .filter(branch -> !branch.getProducts().isEmpty())
                                .map(branch -> {
                                    Product topProduct = branch.getProducts().stream()
                                            .max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock()))
                                            .orElse(null);

                                    return topProduct != null ?
                                            new TopProductDto(topProduct.getName(), branch.getName(), topProduct.getStock()) :
                                            null;
                                })
                                .filter(topProduct -> topProduct != null)
                );
    }

    @Override
    public Mono<FranchiseDto> getFranchiseById(String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(this::mapToDto);
    }

    @Override
    public Flux<FranchiseDto> getAllFranchises() {
        return franchiseRepository.findAll()
                .map(this::mapToDto);
    }

    private FranchiseDto mapToDto(Franchise franchise) {
        FranchiseDto dto = new FranchiseDto();
        dto.setId(franchise.getId());
        dto.setName(franchise.getName());
        dto.setBranches(franchise.getBranches().stream()
                .map(this::mapBranchToDto)
                .toList());
        return dto;
    }

    private BranchDto mapBranchToDto(Branch branch) {
        BranchDto dto = new BranchDto();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setProducts(branch.getProducts().stream()
                .map(this::mapProductToDto)
                .toList());
        return dto;
    }

    private ProductDto mapProductToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        return dto;
    }
}
