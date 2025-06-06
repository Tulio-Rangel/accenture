package com.tulio.accenture.domain.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "franchises")
public class Franchise {
    @Id
    private String id;

    @NotBlank(message = "Franchise name cannot be blank")
    private String name;

    @NotNull(message = "Branches list cannot be null")
    private List<Branch> branches;

    public Franchise() {
        this.branches = new ArrayList<>();
    }

    public Franchise(String name) {
        this.name = name;
        this.branches = new ArrayList<>();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Branch> getBranches() { return branches; }
    public void setBranches(List<Branch> branches) { this.branches = branches; }

    public void addBranch(Branch branch) {
        this.branches.add(branch);
    }
}
