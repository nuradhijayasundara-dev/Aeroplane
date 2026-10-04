package com.airline.booking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    /** Seeds a few flights on first start. */
    @Bean
    CommandLineRunner seed(FlightRepository flights) {
        return args -> {
            if (flights.count() > 0) return;
            LocalDateTime t = LocalDateTime.now().plusDays(3).withHour(8).withMinute(0).withSecond(0).withNano(0);
            flights.save(new Flight("AB101", "New York", "London", t, 520.0, 150));
            flights.save(new Flight("AB202", "London", "Paris", t.plusHours(5), 120.0, 80));
            flights.save(new Flight("AB303", "Paris", "Tokyo", t.plusDays(1), 890.0, 200));
            flights.save(new Flight("AB404", "New York", "Los Angeles", t.plusHours(3), 310.0, 180));
            flights.save(new Flight("AB505", "Dubai", "Mumbai", t.plusDays(2), 240.0, 120));
            flights.save(new Flight("AB606", "London", "New York", t.plusDays(4), 540.0, 150));
        };
    }
}
