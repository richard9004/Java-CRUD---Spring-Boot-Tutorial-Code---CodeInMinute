package com.store.mystore.models;


import jakarta.validation.constraints.*;

public class ProductDto {
    @NotEmpty(message = "name is required")
    private String name;

    @NotEmpty(message = "the brand is required")
    private String brand;

    @NotEmpty(message = "the category is required")
    private String category;

    @Min(0)
    private double price;

    @Min(0)
    public double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) double price) {
        this.price = price;
    }

    public @Size(min = 10, message = "the description should be atleast 10 character") @Size(max = 100, message = "the description should not exceed 200 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "the description should be atleast 10 character") @Size(max = 100, message = "the description should not exceed 200 characters") String description) {
        this.description = description;
    }

    @Size(min = 10, message = "the description should be atleast 10 character")
    @Size(max = 100, message = "the description should not exceed 200 characters")
    private String description;

    public @NotEmpty(message = "name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "the brand is required") String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "the brand is required") String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "the category is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "the category is required") String category) {
        this.category = category;
    }
}
