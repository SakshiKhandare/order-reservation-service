package com.platform.ordering.dto.response;

import com.platform.ordering.entity.enums.OrderStatus;

public class OrderStatusResponse {

    private String orderNumber;
    private OrderStatus status;

    public OrderStatusResponse(String orderNumber, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.status = status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
