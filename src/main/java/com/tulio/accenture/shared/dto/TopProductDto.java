package com.tulio.accenture.shared.dto;

public class TopProductDto {
    private String productName;
    private String branchName;
    private Integer stock;

    public TopProductDto() {}

    public TopProductDto(String productName, String branchName, Integer stock) {
        this.productName = productName;
        this.branchName = branchName;
        this.stock = stock;
    }

    // Getters and Setters
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
