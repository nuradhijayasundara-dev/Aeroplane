package com.airline.booking;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Flight flight;
    private String passengerName;
    private String email;
    private int seats;
    private double totalPrice;
    private String status; // CONFIRMED or CANCELLED
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Booking() {}

    public Booking(Flight flight, String passengerName, String email, int seats) {
        this.flight = flight;
        this.passengerName = passengerName;
        this.email = email;
        this.seats = seats;
        this.totalPrice = flight.getPrice() * seats;
        this.status = "CONFIRMED";
    }

    public Long getId() { return id; }
    public Flight getFlight() { return flight; }
    public String getPassengerName() { return passengerName; }
    public String getEmail() { return email; }
    public int getSeats() { return seats; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
