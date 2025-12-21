package com.platform.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OrderReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderReservationServiceApplication.class, args);
	}

}
