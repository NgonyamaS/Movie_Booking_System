/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

/**
 * A JButton subclass representing one seat in the GUI grid. Colour communicates
 * state: green = available, red = reserved, blue = currently selected (about to
 * be booked).
 */
public class SeatButton extends JButton {

    public static final Color AVAILABLE_COLOR = new Color(46, 204, 113);
    public static final Color RESERVED_COLOR = new Color(231, 76, 60);
    public static final Color SELECTED_COLOR = new Color(52, 152, 219);

    private final String seatId;
    private boolean selectedForBooking;

    public SeatButton(String seatId) {
        super(seatId);
        this.seatId = seatId;
        this.selectedForBooking = false;

        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setFont(new Font("SansSerif", Font.BOLD, 11));
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(48, 32));

        markAvailable();
    }

    public String getSeatId() {
        return seatId;
    }

    public boolean isSelectedForBooking() {
        return selectedForBooking;
    }

    public void markAvailable() {
        selectedForBooking = false;
        setBackground(AVAILABLE_COLOR);
        setEnabled(true);
    }

    public void markReserved() {
        selectedForBooking = false;
        setBackground(RESERVED_COLOR);
        setEnabled(false);
    }

    public void markSelected() {
        selectedForBooking = true;
        setBackground(SELECTED_COLOR);
    }
}
