package com.airline.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCaseOrderByDeparture(
            String origin, String destination);
}
