package org.otopark.ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import org.otopark.model.Payment;
import org.otopark.service.PaymentService;

public class PaymentTable extends JFrame {

    public PaymentTable(List<Payment> allPayments) {
        // Pencere başlığı ve boyutu
        super("Ödeme Listesi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Pencereyi kapatırken sadece bu pencereyi kapatır

        // Tablo sütun başlıkları
        String[] columnNames = {"ID", "Tutar", "Ödeme Yöntemi", "Ödeme Zamanı", "Park Alanı ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Verileri tabloya ekle
        for (Payment payment : allPayments) {
            Object[] rowData = {
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentTime(),
                payment.getParkingSpotId()
            };
            tableModel.addRow(rowData);
        }

        // JTable ve JScrollPane
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Silme butonu
        JButton deleteButton = new JButton("Seçili Ödemeyi Sil");
        add(deleteButton, BorderLayout.SOUTH);

        // Silme işlemi
        deleteButton.addActionListener(e -> {
            PaymentService paymentService = null;
            try {
                paymentService = new PaymentService();
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ödeme servisi başlatılamadı. Hata: " + e1.getMessage());
                return;
            }

            int selectedRow = table.getSelectedRow(); // Seçilen satırı al
            if (selectedRow != -1) { // Eğer bir satır seçilmişse
                int paymentId = (int) tableModel.getValueAt(selectedRow, 0); // ID'yi al
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bu ödemeyi silmek istediğinize emin misiniz?",
                        "Silme Onayı",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Veritabanından ödemeyi sil
                    if (paymentService.deletePaymentById(paymentId)) {
                        // Tablo modelinden satırı kaldır
                        tableModel.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(this, "Ödeme başarıyla silindi.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Ödeme silinirken bir hata oluştu.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen silmek için bir ödeme seçin.");
            }
        });

        setVisible(true);
    }
}