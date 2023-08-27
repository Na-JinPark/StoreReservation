package com.zerobase.StoreReservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StoreReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreReservationApplication.class, args);
	}

}
