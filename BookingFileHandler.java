/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing booking records to a text file
 * (bookings.txt), so that reservations persist between runs
 * of the application. This satisfies the file-handling requirement.
 */

public class BookingFileHandler {
    private static final String FILE_NAME = "bookings.txt";

    /**
     * Appends a single booking record to bookings.txt.
     */
    public static void saveBooking(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(booking.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving booking to file: " + e.getMessage());
        }
    }

    /**
     * Reads bookings.txt (if it exists) and returns every seat ID that has
     * already been reserved, so the GUI can restore seat colours on startup.
     */
    public static List<String> loadReservedSeatIds() {
        List<String> reservedSeatIds = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return reservedSeatIds;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                // Format: timestamp,name,surname,contact,movie,ticketCount,seatId;seatId;...,totalCost
                String[] parts = line.split(",", -1);
                if (parts.length >= 7) {
                    String[] seatIds = parts[6].split(";");
                    for (String id : seatIds) {
                        if (!id.isBlank()) {
                            reservedSeatIds.add(id.trim());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading bookings from file: " + e.getMessage());
        }

        return reservedSeatIds;
    }

}
