package com.platform.ordering.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateOrderRequest {

    @NotBlank
    private String sku;

    @Min(1)
    private int quantity;

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }
}
