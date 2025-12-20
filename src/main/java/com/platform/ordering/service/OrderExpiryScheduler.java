package com.platform.ordering.service;

import org.springframework.scheduling.annotation.Scheduled;

public class OrderExpiryScheduler {

    private final OrderExpiryService expiryService;

    public OrderExpiryScheduler(OrderExpiryService expiryService) {
        this.expiryService = expiryService;
    }

    @Scheduled(fixedDelay = 60000)
    public void runExpiryJob(){
        expiryService.expireReservations();
    }

}
