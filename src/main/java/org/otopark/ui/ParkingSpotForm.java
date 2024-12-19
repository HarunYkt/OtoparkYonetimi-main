package org.otopark.ui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.otopark.model.ParkingSpot;
import org.otopark.service.ParkingSpotService;

public class ParkingSpotForm extends JFrame {

    private JTextField spotNumberField;
    private JCheckBox occupiedCheckBox;
    private JTextField plateNumberField;
    private JTextField hourlyRateField;
    private JButton saveButton;

    private ParkingSpot currentSpot;

    public ParkingSpotForm(ParkingSpot parkingSpot) {
        this.currentSpot = parkingSpot;

        setTitle("Parking Spot Form");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Spot Number
        addComponent(new JLabel("Spot Number:"), 0, 0, gbc);
        spotNumberField = new JTextField(String.valueOf(currentSpot.getSpotNumber()), 15);
        spotNumberField.setEditable(false); // Spot numarası düzenlenemez
        addComponent(spotNumberField, 1, 0, gbc);

        // Is Occupied
        addComponent(new JLabel("Is Occupied:"), 0, 1, gbc);
        occupiedCheckBox = new JCheckBox();
        occupiedCheckBox.setSelected(currentSpot.isOccupied());
        addComponent(occupiedCheckBox, 1, 1, gbc);

        // Plate Number
        addComponent(new JLabel("Plate Number:"), 0, 2, gbc);
        plateNumberField = new JTextField(
            currentSpot.getVehiclePlate() == null ? "" : currentSpot.getVehiclePlate(), 15
        );
        addComponent(plateNumberField, 1, 2, gbc);

        // Hourly Rate
        addComponent(new JLabel("Hourly Rate:"), 0, 3, gbc);
        hourlyRateField = new JTextField(String.valueOf(currentSpot.getHourlyRate()), 15);
        addComponent(hourlyRateField, 1, 3, gbc);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                saveParkingSpot();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(saveButton, gbc);
    }

    private void addComponent(Component component, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }

    private void saveParkingSpot() throws SQLException {
        try {
            // Validasyon: Plaka boş olamaz
            if (plateNumberField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Plate number cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // ParkingSpot nesnesini güncelle
            currentSpot.setVehiclePlate(plateNumberField.getText());
            currentSpot.setOccupied(occupiedCheckBox.isSelected());
            currentSpot.setHourlyRate(Double.parseDouble(hourlyRateField.getText()));

                ParkingSpotService service = new ParkingSpotService();
                service.createSpot(currentSpot);

            JOptionPane.showMessageDialog(this, "Parking spot updated successfully!");
            dispose(); // Formu kapat
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid hourly rate!", "Validation Error", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while updating the parking spot: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}