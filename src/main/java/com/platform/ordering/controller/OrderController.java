package com.platform.ordering.controller;

import com.platform.ordering.dto.request.CreateOrderRequest;
import com.platform.ordering.dto.response.CreateOrderResponse;
import com.platform.ordering.dto.response.OrderStatusResponse;
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
            @Valid @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(request);

        return ResponseEntity.ok(
                new CreateOrderResponse(order.getOrderNumber(), order.getStatus())
        );
    }

    // User abandoned checkout → release held inventory → order is no longer valid.
    @PostMapping("/{orderNumber}/cancel")
    public ResponseEntity<OrderStatusResponse> cancelOrder(
            @PathVariable String orderNumber) {

        Order order = orderService.cancelOrder(orderNumber);

        return ResponseEntity.ok(
                new OrderStatusResponse(order.getOrderNumber(), order.getStatus())
        );
    }

    // Payment succeeded. This order is now final
    @PostMapping("/{orderNumber}/confirm")
    public ResponseEntity<OrderStatusResponse> confirmOrder(
            @PathVariable String orderNumber) {

        Order order = orderService.confirmOrder(orderNumber);

        return ResponseEntity.ok(
                new OrderStatusResponse(order.getOrderNumber(), order.getStatus())
        );
    }
}

