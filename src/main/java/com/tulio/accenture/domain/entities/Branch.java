package com.tulio.accenture.domain.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Branch {
    @Id
    private String id;

    @NotBlank(message = "Branch name cannot be blank")
    private String name;

    @NotNull(message = "Products list cannot be null")
    private List<Product> products;

    public Branch() {
        this.products = new ArrayList<>();
    }

    public Branch(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public boolean removeProduct(String productId) {
        return this.products.removeIf(product -> product.getId().equals(productId));
    }
}
