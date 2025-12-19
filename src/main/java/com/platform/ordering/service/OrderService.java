package com.platform.ordering.service;

import com.platform.ordering.entity.InventoryReservation;
import com.platform.ordering.entity.Order;
import com.platform.ordering.entity.OrderItem;
import com.platform.ordering.entity.Product;
import com.platform.ordering.repository.OrderRepository;
import com.platform.ordering.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {

    private final InventoryService inventoryService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(InventoryService inventoryService,
                        ProductRepository productRepository,
                        OrderRepository orderRepository) {
        this.inventoryService = inventoryService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order createOrder(String sku, int quantity) {

        // Create order with business identifier
        Order order = new Order(generateOrderNumber());

        // Reserve inventory
        InventoryReservation reservation =
                inventoryService.reserve(sku, quantity);

        // Create order item
        Product product = reservation.getProduct();
        OrderItem item = new OrderItem(product, quantity);
        order.addItem(item);

        // Persist order (items cascade)
        return orderRepository.save(order);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID();
    }
}
