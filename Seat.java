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
 * Represents a single seat in the theatre.
 * Each seat has a row letter, a seat number, and a reserved state.
 */

public class Seat {
    private final char row;
    private final int number;
    private boolean reserved;

    public Seat(char row, int number) {
        this.row = row;
        this.number = number;
        this.reserved = false;
    }

    public char getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    /**
     * Returns the seat identifier, e.g. "A1", "C10".
     */
    public String getSeatId() {
        return "" + row + number;
    }

    @Override
    public String toString() {
        return getSeatId();
    }

}
