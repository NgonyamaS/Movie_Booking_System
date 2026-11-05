/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.movietheatrebooking;

/**
 *
 * @author Admin
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * The main application window. Holds the customer-details form, the movie
 * selector, the 5x10 seat grid, and the booking logic that ties them all
 * together.
 */
public class BookingFrame extends JFrame {

    private final Theatre theatre;
    private final List<Movie> movies;
    private final java.util.Map<String, SeatButton> seatButtons;
    private final List<String> selectedSeatIds;

    private JTextField nameField;
    private JTextField surnameField;
    private JTextField contactField;
    private JTextField ticketsField;
    private JComboBox<Movie> movieComboBox;
    private JLabel statusLabel;
    private JLabel selectionLabel;

    public BookingFrame() {
        theatre = new Theatre();
        movies = createMovieList();
        seatButtons = new java.util.LinkedHashMap<>();
        selectedSeatIds = new ArrayList<>();

        setTitle("Movie Theatre Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buildCustomerPanel(), BorderLayout.NORTH);
        add(buildSeatPanel(), BorderLayout.CENTER);
        add(buildActionPanel(), BorderLayout.SOUTH);

        restorePreviousBookings();

        setMinimumSize(new java.awt.Dimension(680, 620));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Any five movies, as allowed by the project brief.
     */
    private List<Movie> createMovieList() {
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Avengers", 85.00));
        list.add(new Movie("Black Panther", 85.00));
        list.add(new Movie("Avatar", 90.00));
        list.add(new Movie("Mission Impossible", 80.00));
        list.add(new Movie("Inside Out", 75.00));
        return list;
    }

    // ---------------------------------------------------------------
    // UI construction
    // ---------------------------------------------------------------
    private JPanel buildCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 4, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Customer Details"));

        nameField = new JTextField();
        surnameField = new JTextField();
        contactField = new JTextField();
        ticketsField = new JTextField();
        movieComboBox = new JComboBox<>(movies.toArray(new Movie[0]));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Surname:"));
        panel.add(surnameField);

        panel.add(new JLabel("Contact Number:"));
        panel.add(contactField);
        panel.add(new JLabel("Number of Tickets:"));
        panel.add(ticketsField);

        panel.add(new JLabel("Movie:"));
        panel.add(movieComboBox);

        return panel;
    }

    private JPanel buildSeatPanel() {
        JPanel outer = new JPanel(new BorderLayout(5, 5));
        outer.setBorder(BorderFactory.createTitledBorder("Select Seats (screen this way)"));

        JPanel grid = new JPanel(new GridLayout(Theatre.ROWS.length, Theatre.SEATS_PER_ROW, 6, 6));
        for (char row : Theatre.ROWS) {
            for (int number = 1; number <= Theatre.SEATS_PER_ROW; number++) {
                String seatId = "" + row + number;
                SeatButton seatButton = new SeatButton(seatId);
                seatButton.addActionListener(this::onSeatClicked);
                seatButtons.put(seatId, seatButton);
                grid.add(seatButton);
            }
        }
        outer.add(grid, BorderLayout.CENTER);

        JPanel legend = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        legend.add(legendItem("Available", SeatButton.AVAILABLE_COLOR));
        legend.add(legendItem("Selected", SeatButton.SELECTED_COLOR));
        legend.add(legendItem("Reserved", SeatButton.RESERVED_COLOR));
        outer.add(legend, BorderLayout.SOUTH);

        return outer;
    }

    private JPanel legendItem(String text, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel swatch = new JLabel("   ");
        swatch.setOpaque(true);
        swatch.setBackground(color);
        item.add(swatch);
        item.add(new JLabel(text));
        return item;
    }

    private JPanel buildActionPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));

        selectionLabel = new JLabel("Selected seats: none");
        selectionLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(selectionLabel, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton bookButton = new JButton("Book Tickets");
        JButton clearButton = new JButton("Clear Selection");
        bookButton.addActionListener(this::onBookClicked);
        clearButton.addActionListener(this::onClearClicked);
        buttons.add(bookButton);
        buttons.add(clearButton);
        panel.add(buttons, BorderLayout.CENTER);

        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    // ---------------------------------------------------------------
    // Event handling
    // ---------------------------------------------------------------
    private void onSeatClicked(ActionEvent event) {
        SeatButton clicked = (SeatButton) event.getSource();

        if (!theatre.isSeatAvailable(clicked.getSeatId())) {
            // Should not normally happen since reserved seats are disabled,
            // but this guard keeps double booking impossible either way.
            JOptionPane.showMessageDialog(this, "That seat is already reserved.",
                    "Seat Unavailable", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (clicked.isSelectedForBooking()) {
            clicked.markAvailable();
            selectedSeatIds.remove(clicked.getSeatId());
        } else {
            clicked.markSelected();
            selectedSeatIds.add(clicked.getSeatId());
        }

        updateSelectionLabel();
    }

    private void onClearClicked(ActionEvent event) {
        for (String seatId : new ArrayList<>(selectedSeatIds)) {
            seatButtons.get(seatId).markAvailable();
        }
        selectedSeatIds.clear();
        updateSelectionLabel();
        statusLabel.setText(" ");
    }

    private void onBookClicked(ActionEvent event) {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String contact = contactField.getText().trim();
        String ticketsText = ticketsField.getText().trim();

        // --- Input validation ---
        if (name.isEmpty() || surname.isEmpty() || contact.isEmpty() || ticketsText.isEmpty()) {
            showError("Please fill in all customer details.");
            return;
        }

        if (!contact.matches("\\d{7,15}")) {
            showError("Contact number must contain digits only (7-15 digits).");
            return;
        }

        int numberOfTickets;
        try {
            numberOfTickets = Integer.parseInt(ticketsText);
        } catch (NumberFormatException e) {
            showError("Number of tickets must be a whole number.");
            return;
        }

        if (numberOfTickets <= 0) {
            showError("Number of tickets must be greater than zero.");
            return;
        }

        if (selectedSeatIds.isEmpty()) {
            showError("Please select at least one seat.");
            return;
        }

        if (selectedSeatIds.size() != numberOfTickets) {
            showError("You selected " + selectedSeatIds.size() + " seat(s) but requested "
                    + numberOfTickets + " ticket(s). These must match.");
            return;
        }

        Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();

        // --- Re-check availability and reserve (prevents double booking) ---
        for (String seatId : selectedSeatIds) {
            if (!theatre.isSeatAvailable(seatId)) {
                showError("Seat " + seatId + " was just booked by someone else. Please choose again.");
                return;
            }
        }
        for (String seatId : selectedSeatIds) {
            theatre.reserveSeat(seatId);
            seatButtons.get(seatId).markReserved();
        }

        Customer customer = new Customer(name, surname, contact, numberOfTickets);
        Booking booking = new Booking(customer, selectedMovie, new ArrayList<>(selectedSeatIds));
        BookingFileHandler.saveBooking(booking);

        JOptionPane.showMessageDialog(this,
                "Booking confirmed!\n\n"
                + "Customer: " + customer.getFullName() + "\n"
                + "Movie: " + selectedMovie.getTitle() + "\n"
                + "Seats: " + String.join(", ", booking.getSeatIds()) + "\n"
                + "Total: R" + String.format("%.2f", booking.getTotalCost()),
                "Booking Successful", JOptionPane.INFORMATION_MESSAGE);

        statusLabel.setText("Booking saved for " + customer.getFullName() + ".");
        selectedSeatIds.clear();
        updateSelectionLabel();
        clearCustomerForm();
    }

    // ---------------------------------------------------------------
    // Helpers
    // ---------------------------------------------------------------
    private void updateSelectionLabel() {
        if (selectedSeatIds.isEmpty()) {
            selectionLabel.setText("Selected seats: none");
        } else {
            selectionLabel.setText("Selected seats: " + String.join(", ", selectedSeatIds));
        }
    }

    private void clearCustomerForm() {
        nameField.setText("");
        surnameField.setText("");
        contactField.setText("");
        ticketsField.setText("");
    }

    private void showError(String message) {
        statusLabel.setText(message);
        JOptionPane.showMessageDialog(this, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * On startup, reads bookings.txt and marks any previously reserved seats as
     * red, so reservations persist between runs of the app.
     */
    private void restorePreviousBookings() {
        List<String> reservedSeatIds = BookingFileHandler.loadReservedSeatIds();
        for (String seatId : reservedSeatIds) {
            if (theatre.reserveSeat(seatId)) {
                SeatButton button = seatButtons.get(seatId);
                if (button != null) {
                    button.markReserved();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingFrame frame = new BookingFrame();
            frame.setVisible(true);
        });
    }
}
