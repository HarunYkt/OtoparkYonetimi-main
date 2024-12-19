package org.otopark.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.otopark.model.Customer;
import org.otopark.model.Vehicle;
import org.otopark.service.CustomerService;
import org.otopark.service.VehicleService;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VehicleTable extends JFrame {


    public VehicleTable(List<Vehicle> vehicles){
        super("Arac Listesi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Plaka", "Arac Tipi","Sahibi"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);


        for (Vehicle vehicle: vehicles) {
            Object[] rowData={
                vehicle.getId(),
                vehicle.getPlateNumber(),
                vehicle.getVehicleType(),
                vehicle.getOwner()
            };
            tableModel.addRow(rowData);
        }

        JTable table=new JTable(tableModel);
        JScrollPane scrollPane=new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Secili Araci sil");
        add(deleteButton, BorderLayout.SOUTH);


        deleteButton.addActionListener(e->{
            VehicleService vehicleService=null;
            try{
                vehicleService=new VehicleService();
            }catch(SQLException e1){    
                e1.printStackTrace();
               JOptionPane.showMessageDialog(this, "Arac servisi başlatılamadı. Hata: " + e1.getMessage());
                return;
            }

            int selectedRow = table.getSelectedRow(); // Seçilen satırı al
            if (selectedRow != -1) { // Eğer bir satır seçilmişse
                int customerId = (int) tableModel.getValueAt(selectedRow, 0); // ID'yi al
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bu müşteriyi silmek istediğinize emin misiniz?", 
                    "Silme Onayı", 
                    JOptionPane.YES_NO_OPTION);
    
                if (confirm == JOptionPane.YES_OPTION) {
                    // Veritabanından müşteriyi sil
                    if (vehicleService.deleteCustomerById(customerId)) {
                        // Tablo modelinden satırı kaldır
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Müşteri başarıyla silindi.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Müşteri silinirken bir hata oluştu.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen silmek için bir müşteri seçin.");
            }
        });

        setVisible(true);

    }
    
}
