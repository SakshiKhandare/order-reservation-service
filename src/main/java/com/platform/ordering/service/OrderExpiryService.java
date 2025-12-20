package com.platform.ordering.service;

import com.platform.ordering.entity.InventoryReservation;
import com.platform.ordering.repository.InventoryReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class OrderExpiryService {

    private final InventoryReservationRepository reservationRepository;
    private final InventoryService inventoryService;

    public OrderExpiryService(InventoryReservationRepository reservationRepository,
                              InventoryService inventoryService) {
        this.reservationRepository = reservationRepository;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public void expireReservations(){

        List<InventoryReservation> expiredReservations=
                reservationRepository.findByActiveTrueAndExpiresAtBefore(Instant.now());

        for (InventoryReservation reservation: expiredReservations){
            inventoryService.releaseReservation(reservation);
            if (reservation.getOrder() != null) {
                reservation.getOrder().expire();
            }
        }
    }

}
