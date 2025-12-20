package com.platform.ordering.service;

import com.platform.ordering.entity.InventoryReservation;
import com.platform.ordering.entity.Order;
import com.platform.ordering.entity.Product;
import com.platform.ordering.repository.InventoryReservationRepository;
import com.platform.ordering.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class InventoryService {

    private static final int RESERVATION_TTL_SECONDS = 300; // 5 minutes

    private final ProductRepository productRepository;
    private final InventoryReservationRepository inventoryReservationRepository;

    public InventoryService(ProductRepository productRepository,
                            InventoryReservationRepository inventoryReservationRepository) {
        this.productRepository = productRepository;
        this.inventoryReservationRepository = inventoryReservationRepository;
    }

    @Transactional
    public InventoryReservation reserve(String sku, int quantity, Order order) {

        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.decreaseQuantity(quantity);

        InventoryReservation reservation =
                new InventoryReservation(
                        product,
                        quantity,
                        order,
                        Instant.now().plusSeconds(300) // 5 min hold
                );

        return inventoryReservationRepository.save(reservation);
    }

    @Transactional
    public void releaseReservation(InventoryReservation reservation) {

        if (!reservation.isActive()) {
            return; // idempotent safety
        }

        Product product = reservation.getProduct();
        product.increaseQuantity(reservation.getQuantity());

        reservation.deactivate();
    }

}
