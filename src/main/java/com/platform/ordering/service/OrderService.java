package com.platform.ordering.service;

import com.platform.ordering.dto.request.CreateOrderRequest;
import com.platform.ordering.dto.request.OrderItemRequest;
import com.platform.ordering.entity.InventoryReservation;
import com.platform.ordering.entity.Order;
import com.platform.ordering.entity.OrderItem;
import com.platform.ordering.entity.Product;
import com.platform.ordering.entity.enums.OrderStatus;
import com.platform.ordering.repository.InventoryReservationRepository;
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
    private final InventoryReservationRepository inventoryReservationRepository;

    public OrderService(InventoryService inventoryService,
                        ProductRepository productRepository,
                        OrderRepository orderRepository,
                        InventoryReservationRepository inventoryReservationRepository) {
        this.inventoryService = inventoryService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.inventoryReservationRepository = inventoryReservationRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        Order order = new Order(generateOrderNumber());

        // Save order FIRST (so it gets an ID)
        orderRepository.save(order);

        // Now reserve inventory
        for (OrderItemRequest itemReq : request.getItems()) {

            InventoryReservation reservation =
                    inventoryService.reserve(
                            itemReq.getSku(),
                            itemReq.getQuantity(),
                            order
                    );

            Product product = reservation.getProduct();
            OrderItem item = new OrderItem(product, itemReq.getQuantity());
            order.addItem(item);
        }

        // Save order again (items cascade)
        return orderRepository.save(order);
    }


    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID();
    }

    @Transactional
    public Order cancelOrder(String orderNumber){

        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        if(order.getStatus() != OrderStatus.RESERVED){
            throw new IllegalStateException("Only reserved orders can be cancelled");
        }

        // release inventory for each item
        order.getItems().forEach(item ->{
            InventoryReservation reservation =
                    inventoryReservationRepository
                            .findByProductAndActiveTrue(item.getProduct())
                            .orElseThrow(() -> new IllegalStateException("Active reservation not found"));

            inventoryService.releaseReservation(reservation);
        });

        order.cancel();
        return order;
    }

    @Transactional
    public Order confirmOrder(String orderNumber){

        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.confirm();

        // deactivate reservation (inventory stays reduced)
        order.getItems().forEach(item -> {
           inventoryReservationRepository
                   .findByProductAndActiveTrue(item.getProduct())
                   .ifPresent(InventoryReservation::deactivate);
        });

        return order;
    }


}
