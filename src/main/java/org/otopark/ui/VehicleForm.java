package org.otopark.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.otopark.model.Customer;
import org.otopark.model.Vehicle;
import org.otopark.service.CustomerService;
import org.otopark.service.VehicleService;


public class VehicleForm {

    public VehicleForm() {
        // JFrame oluştur
        JFrame frame = new JFrame("Yeni Araç Ekle");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // GridBagConstraints ayarları
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Plaka Numarası etiketi ve textfield
        JLabel plateNumberLabel = new JLabel("Plaka Numarası:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(plateNumberLabel, gbc);

        JTextField plateNumberField = new JTextField(20); // Boyut genişletildi
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Genişliği esnek hale getirin
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(plateNumberField, gbc);

        // Araç Tipi etiketi ve textfield
        JLabel vehicleTypeLabel = new JLabel("Araç Tipi:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(vehicleTypeLabel, gbc);

        JTextField vehicleTypeField = new JTextField(20); // Boyut genişletildi
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Genişliği esnek hale getirin
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(vehicleTypeField, gbc);

        // Müşteri etiketi ve combobox
        JLabel customerLabel = new JLabel("Müşteri:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(customerLabel, gbc);

        JComboBox<Customer> customerComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0; // Genişliği esnek hale getirin
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(customerComboBox, gbc);

        // Müşteri seçenekleri yükleme
        customerComboBox.addItem(null); // "Müşteri Yok" seçeneği
        try {
            CustomerService customerService = new CustomerService();
            for (Customer customer : customerService.getAllCustomers()) {
                customerComboBox.addItem(customer);
            }

            // Cep telefonu numarasını gösterecek cell renderer ekleyelim
            customerComboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    if (value != null) {
                        // Müşterinin sadece telefon numarasını göster
                        value = ((Customer) value).getPhoneNumber();
                    }
                    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Müşteri bilgileri yüklenirken bir hata oluştu.");
        }

        // Uyarı etiketi
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(messageLabel, gbc);

        // Kaydet butonu
        JButton saveButton = new JButton("Kaydet");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(saveButton, gbc);

        // Buton tıklama işlemi
        saveButton.addActionListener(e -> {
            String plateNumber = plateNumberField.getText();
            String vehicleType = vehicleTypeField.getText();
            Customer customer = (Customer) customerComboBox.getSelectedItem();

            if (plateNumber.isEmpty() || vehicleType.isEmpty()) {
                messageLabel.setText("Plaka Numarası ve Araç Tipi zorunludur!");
                return;
            }

            Vehicle vehicle = new Vehicle();
            vehicle.setPlateNumber(plateNumber);
            vehicle.setVehicleType(vehicleType);
            vehicle.setOwner(customer);

            try {
                VehicleService vehicleService = new VehicleService();
                vehicleService.createVehicle(vehicle);
                JOptionPane.showMessageDialog(frame, "Araç başarıyla kaydedildi!");
                frame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Araç kaydedilemedi!");
            }
        });

        // JFrame görünür hale getir
        frame.setVisible(true);
    }
}