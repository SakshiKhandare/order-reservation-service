package com.platform.ordering.entity;

import com.platform.ordering.entity.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Order() {
        // for JPA
    }

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
        this.status = OrderStatus.RESERVED;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        item.attachToOrder(this);
    }

    public void confirm() {
        if (this.status != OrderStatus.RESERVED) {
            throw new IllegalStateException("Only reserved orders can be confirmed");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Confirmed orders cannot be cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public void expire() {
        if (this.status == OrderStatus.RESERVED) {
            this.status = OrderStatus.EXPIRED;
        }
    }
}
