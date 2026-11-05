/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Represents a completed booking: which customer booked which seats
 * for which movie, and how much it cost.
 */
public class Booking {

    private final Customer customer;
    private final Movie movie;
    private final List<String> seatIds;
    private final double totalCost;
    private final String timestamp;

    public Booking(Customer customer, Movie movie, List<String> seatIds) {
        this.customer = customer;
        this.movie = movie;
        this.seatIds = seatIds;
        this.totalCost = movie.getTicketPrice() * seatIds.size();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Customer getCustomer() {
        return customer;
    }

    public Movie getMovie() {
        return movie;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Serialises this booking as one comma-separated line for saving to file.
     * Format: timestamp,name,surname,contact,movieTitle,ticketCount,seatId;seatId;...,totalCost
     */
    public String toFileString() {
        return String.join(",",
                timestamp,
                customer.getName(),
                customer.getSurname(),
                customer.getContactNumber(),
                movie.getTitle(),
                String.valueOf(seatIds.size()),
                String.join(";", seatIds),
                String.format("%.2f", totalCost));
    }
}

