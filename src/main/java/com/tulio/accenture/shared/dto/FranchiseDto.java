package com.tulio.accenture.shared.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class FranchiseDto {
    private String id;

    @NotBlank(message = "Franchise name cannot be blank")
    private String name;

    private List<BranchDto> branches;

    public FranchiseDto() {}

    public FranchiseDto(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<BranchDto> getBranches() { return branches; }
    public void setBranches(List<BranchDto> branches) { this.branches = branches; }
}

