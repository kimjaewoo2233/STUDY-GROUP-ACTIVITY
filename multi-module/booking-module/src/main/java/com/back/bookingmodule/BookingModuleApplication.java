package com.back.bookingmodule;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
public class BookingModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingModuleApplication.class, args);
    }

}
