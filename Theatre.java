/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the theatre's 50 seats (5 rows A-E, 10 seats each). Uses a
 * LinkedHashMap so seats keep insertion order (A1..A10, B1..B10, ...) while
 * still being looked up quickly by seat ID.
 */
public class Theatre {

    public static final char[] ROWS = {'A', 'B', 'C', 'D', 'E'};
    public static final int SEATS_PER_ROW = 10;

    private final Map<String, Seat> seats;

    public Theatre() {
        seats = new LinkedHashMap<>();
        for (char row : ROWS) {
            for (int number = 1; number <= SEATS_PER_ROW; number++) {
                Seat seat = new Seat(row, number);
                seats.put(seat.getSeatId(), seat);
            }
        }
    }

    public Map<String, Seat> getSeats() {
        return seats;
    }

    public Seat getSeat(String seatId) {
        return seats.get(seatId);
    }

    public boolean isSeatAvailable(String seatId) {
        Seat seat = seats.get(seatId);
        return seat != null && !seat.isReserved();
    }

    /**
     * Reserves a seat. Returns false if the seat does not exist or is already
     * reserved (this is the guard that prevents double booking).
     */
    public boolean reserveSeat(String seatId) {
        Seat seat = seats.get(seatId);
        if (seat == null || seat.isReserved()) {
            return false;
        }
        seat.setReserved(true);
        return true;
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> available = new ArrayList<>();
        for (Seat seat : seats.values()) {
            if (!seat.isReserved()) {
                available.add(seat);
            }
        }
        return available;
    }

}
