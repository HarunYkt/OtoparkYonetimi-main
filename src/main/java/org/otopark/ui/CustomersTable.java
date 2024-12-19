package org.otopark.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.otopark.model.Customer;
import org.otopark.service.CustomerService;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CustomersTable extends JFrame {

    public CustomersTable(List<Customer> customers) {
        // Pencere başlığı ve boyutu
        super("Müşteri Listesi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Sadece bu pencereyi kapatır

        // Tablo sütun başlıkları
        String[] columnNames = {"ID", "Ad", "Soyad", "Email","Telefon","Yas"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Verileri tabloya ekle
        for (Customer customer : customers) {
            Object[] rowData = {
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAge()
            };
            tableModel.addRow(rowData);
        }

        // JTable ve JScrollPane
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Seçili Müşteriyi Sil");
        add(deleteButton, BorderLayout.SOUTH);


        deleteButton.addActionListener(e->{
            CustomerService customerService=null;
            try {
                customerService = new CustomerService();
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Müşteri servisi başlatılamadı. Hata: " + e1.getMessage());
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
                    if (customerService.deleteCustomerById(customerId)) {
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