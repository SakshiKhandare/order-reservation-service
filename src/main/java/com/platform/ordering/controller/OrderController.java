package com.platform.ordering.controller;

import com.platform.ordering.dto.request.CreateOrderRequest;
import com.platform.ordering.dto.response.CreateOrderResponse;
import com.platform.ordering.entity.Order;
import com.platform.ordering.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // It creates an order and reserves inventory atomically so that stock is not oversold.
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody @Valid CreateOrderRequest request) {

        Order order = orderService.createOrder(
                request.getSku(),
                request.getQuantity()
        );

        return ResponseEntity.ok(
                new CreateOrderResponse(
                        order.getOrderNumber(),
                        order.getStatus()
                )
        );
    }
}
