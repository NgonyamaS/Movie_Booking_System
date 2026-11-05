/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */
public class Movie {

    private final String title;
    private final double ticketPrice;

    public Movie(String title, double ticketPrice) {
        this.title = title;
        this.ticketPrice = ticketPrice;
    }

    public String getTitle() {
        return title;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        // Shown directly in the JComboBox
        return title + " (R" + String.format("%.2f", ticketPrice) + ")";
    }

}
