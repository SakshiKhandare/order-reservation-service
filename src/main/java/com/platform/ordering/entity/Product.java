package com.platform.ordering.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stock Keeping Unit -> sku
    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Version
    private Long version;

    protected Product() {
        // for JPA
    }

    public Product(String sku, String name, Integer availableQuantity) {
        this.sku = sku;
        this.name = name;
        this.availableQuantity = availableQuantity;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void decreaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.availableQuantity < quantity) {
            throw new IllegalStateException("Insufficient inventory");
        }
        this.availableQuantity -= quantity;
    }

    public void increaseQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.availableQuantity += quantity;
    }
}
