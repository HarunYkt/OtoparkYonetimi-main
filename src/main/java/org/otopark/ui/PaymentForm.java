package org.otopark.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.otopark.model.Customer;
import org.otopark.model.ParkingSpot;
import org.otopark.model.Payment;
import org.otopark.service.CustomerService;
import org.otopark.service.PaymentService;

public class PaymentForm extends JFrame {

    private CustomerService customerService;
    private PaymentService paymentService; 

    public PaymentForm() {

        try {
            customerService = new CustomerService();
            paymentService = new PaymentService();

        } catch (Exception e) { 
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + e.getMessage());
            e.printStackTrace();
        }

        setTitle("Ödeme Formu");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10)); // Daha geniş bir form tasarımı

        // Alanlar
        JLabel lblPhoneNumber = new JLabel("Cep Telefonu:");
        JTextField txtPhoneNumber = new JTextField();

        JLabel lblCustomerName = new JLabel("Müşteri Adı:");
        JTextField txtCustomerName = new JTextField();
        txtCustomerName.setEditable(false); // Sadece okuma yapılabilir

        JLabel lblSpotNumber = new JLabel("Park Alanı No:");
        JTextField txtSpotNumber = new JTextField();

        JLabel lblAmount = new JLabel("Tutar:");
        JTextField txtAmount = new JTextField();

        JLabel lblPaymentMethod = new JLabel("Ödeme Yöntemi:");
        JComboBox<String> cbPaymentMethod = new JComboBox<>(new String[]{"Nakit", "Kredi Kartı", "Dijital"});

        JButton btnSubmit = new JButton("Ödeme Yap");

        // Form bileşenlerini ekle
        add(lblPhoneNumber);
        add(txtPhoneNumber);
        add(lblCustomerName);
        add(txtCustomerName);
        add(lblSpotNumber);
        add(txtSpotNumber);
        add(lblAmount);
        add(txtAmount);
        add(lblPaymentMethod);
        add(cbPaymentMethod);
        add(new JLabel()); // Boş alan
        add(btnSubmit);

        // Cep telefonu girildiğinde müşteri sorgulama
        txtPhoneNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = txtPhoneNumber.getText();
                Customer customer = customerService.getCustomerByPhone(phoneNumber);

                if (customer != null) {
                    txtCustomerName.setText(customer.getName()); // Müşteri adını göster
                } else {
                    txtCustomerName.setText(""); // Temizle
                }
            }
        });

        // Ödeme işlemi
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String phoneNumber = txtPhoneNumber.getText();
                    Customer customer = customerService.getCustomerByPhone(phoneNumber);

                    int spotNumber = Integer.parseInt(txtSpotNumber.getText());
                    double amount = Double.parseDouble(txtAmount.getText());
                    String method = (String) cbPaymentMethod.getSelectedItem();

                    // İndirim uygulanacak mı?
                    if (customer != null) {
                        amount = amount * 0.90; // %10 indirim
                        JOptionPane.showMessageDialog(null, "İndirim uygulandı: Yeni Tutar " + amount);
                    }

                    // ParkingSpot'u veritabanından al
                    ParkingSpot parkingSpot = customerService.getParkingSpotBySpotNumber(spotNumber);

                    // Payment nesnesi oluştur
                    Payment payment = new Payment();
                    payment.setAmount(amount);
                    payment.setPaymentTime(new Date());
                    payment.setPaymentMethod(method);
                    payment.setParkingSpotId(parkingSpot.getId());

                    // Ödemeyi servise gönder
                    boolean success = paymentService.makePayment(payment);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "Ödeme başarılı!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Ödeme başarısız!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }
}