package com.inventory.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

// Maps to "products" collection in MongoDB
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull @Min(0)
    private Double price;

    @NotNull @Min(0)
    private Integer quantity;

    private String description;

    // Constructors
    public Product() {}

    public Product(String name, String category, Double price, Integer quantity, String description) {
        this.name        = name;
        this.category    = category;
        this.price       = price;
        this.quantity    = quantity;
        this.description = description;
    }

    // Getters & Setters
    public String  getId()          { return id; }
    public void    setId(String id) { this.id = id; }

    public String  getName()             { return name; }
    public void    setName(String name)  { this.name = name; }

    public String  getCategory()                  { return category; }
    public void    setCategory(String category)   { this.category = category; }

    public Double  getPrice()                { return price; }
    public void    setPrice(Double price)    { this.price = price; }

    public Integer getQuantity()                   { return quantity; }
    public void    setQuantity(Integer quantity)   { this.quantity = quantity; }

    public String  getDescription()                       { return description; }
    public void    setDescription(String description)     { this.description = description; }
}
