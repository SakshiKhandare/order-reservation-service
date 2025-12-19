package com.platform.ordering.service;

import com.platform.ordering.entity.InventoryReservation;
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
    private final InventoryReservationRepository reservationRepository;

    public InventoryService(ProductRepository productRepository,
                            InventoryReservationRepository reservationRepository) {
        this.productRepository = productRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public InventoryReservation reserve(String sku, int quantity) {

        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // optimistic locking happens here
        product.decreaseQuantity(quantity);

        InventoryReservation reservation = new InventoryReservation(
                product,
                quantity,
                Instant.now().plusSeconds(RESERVATION_TTL_SECONDS)
        );

        reservationRepository.save(reservation);

        return reservation;
    }
}
