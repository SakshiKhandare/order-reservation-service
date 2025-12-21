package com.platform.ordering.dto.response;

import java.time.Instant;
import java.util.List;

public class OrderDetailsResponse {

    private String orderNumber;
    private String status;
    private Instant createdAt;
    private List<Item> items;

    public OrderDetailsResponse(
            String orderNumber,
            String status,
            Instant createdAt,
            List<Item> items) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public record Item(String sku, Integer quantity) {}

}
