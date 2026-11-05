/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */
/**
 * Represents the customer making a booking.
 */

public class Customer {
     private final String name;
    private final String surname;
    private final String contactNumber;
    private final int numberOfTickets;

    public Customer(String name, String surname, String contactNumber, int numberOfTickets) {
        this.name = name;
        this.surname = surname;
        this.contactNumber = contactNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public String getFullName() {
        return name + " " + surname;
    }

}
