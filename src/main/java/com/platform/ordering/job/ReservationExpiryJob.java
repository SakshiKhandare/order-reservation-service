package com.platform.ordering.job;

import com.platform.ordering.entity.InventoryReservation;
import com.platform.ordering.repository.InventoryReservationRepository;
import com.platform.ordering.service.InventoryService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;

public class ReservationExpiryJob {

    private final InventoryReservationRepository reservationRepository;
    private final InventoryService inventoryService;

    public ReservationExpiryJob(
            InventoryReservationRepository reservationRepository,
            InventoryService inventoryService) {
        this.reservationRepository = reservationRepository;
        this.inventoryService = inventoryService;
    }

    @Scheduled(fixedDelay = 60000) // every 1 min
    @Transactional
    public void expireReservations(){
        List<InventoryReservation> expired =
                reservationRepository.findByActiveTrueAndExpiresAtBefore(Instant.now());

        for (InventoryReservation reservation: expired){
            inventoryService.release(reservation);
        }
    }

}
