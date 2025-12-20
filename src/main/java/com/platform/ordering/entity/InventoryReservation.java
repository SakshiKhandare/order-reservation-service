package com.platform.ordering.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "inventory_reservations")
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    protected InventoryReservation() {
        // for JPA
    }

    public InventoryReservation(Product product, Integer quantity, Order order, Instant expiresAt) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.product = product;
        this.quantity = quantity;
        this.order = order;
        this.expiresAt = expiresAt;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Boolean isActive() {
        return active;
    }

    public Order getOrder() {
        return order;
    }
}

