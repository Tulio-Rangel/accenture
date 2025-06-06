package com.tulio.accenture.shared.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class BranchDto {
    private String id;

    @NotBlank(message = "Branch name cannot be blank")
    private String name;

    private List<ProductDto> products;

    public BranchDto() {}

    public BranchDto(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ProductDto> getProducts() { return products; }
    public void setProducts(List<ProductDto> products) { this.products = products; }
}
