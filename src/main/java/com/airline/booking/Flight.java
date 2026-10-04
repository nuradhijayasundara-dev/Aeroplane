package com.airline.booking;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departure;
    private double price;
    private int availableSeats;

    protected Flight() {}

    public Flight(String flightNumber, String origin, String destination,
                  LocalDateTime departure, double price, int availableSeats) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public Long getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDateTime getDeparture() { return departure; }
    public double getPrice() { return price; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
