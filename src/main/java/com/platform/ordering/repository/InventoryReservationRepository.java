package com.platform.ordering.repository;

import com.platform.ordering.entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, Long> {

    List<InventoryReservation> findByActiveTrueAndExpiresAtBefore(Instant now);
}
