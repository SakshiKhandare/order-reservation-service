package com.platform.ordering.repository;

import com.platform.ordering.entity.InventoryReservation;
import com.platform.ordering.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, Long> {

    List<InventoryReservation> findByActiveTrueAndExpiresAtBefore(Instant now);

    Optional<InventoryReservation> findByProductAndActiveTrue(Product product);
}
