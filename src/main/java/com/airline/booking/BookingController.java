package com.airline.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    public record BookingRequest(
            @NotNull Long flightId,
            @NotBlank String passengerName,
            @Email @NotBlank String email,
            @Min(1) @Max(9) int seats) {}

    private final FlightRepository flights;
    private final BookingRepository bookings;

    public BookingController(FlightRepository flights, BookingRepository bookings) {
        this.flights = flights;
        this.bookings = bookings;
    }

    @GetMapping("/flights")
    public List<Flight> searchFlights(@RequestParam(defaultValue = "") String from,
                                      @RequestParam(defaultValue = "") String to) {
        return flights.findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCaseOrderByDeparture(from, to);
    }

    @PostMapping("/bookings")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Booking book(@Valid @RequestBody BookingRequest req) {
        Flight flight = flights.findById(req.flightId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        if (flight.getAvailableSeats() < req.seats()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Only " + flight.getAvailableSeats() + " seats left on this flight");
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - req.seats());
        return bookings.save(new Booking(flight, req.passengerName(), req.email(), req.seats()));
    }

    @GetMapping("/bookings")
    public List<Booking> myBookings(@RequestParam String email) {
        return bookings.findByEmailIgnoreCaseOrderByCreatedAtDesc(email);
    }

    @DeleteMapping("/bookings/{id}")
    @Transactional
    public Booking cancel(@PathVariable Long id) {
        Booking b = bookings.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));
        if (!"CANCELLED".equals(b.getStatus())) {
            b.setStatus("CANCELLED");
            Flight f = b.getFlight();
            f.setAvailableSeats(f.getAvailableSeats() + b.getSeats());
        }
        return b;
    }
}
