package org.otopark.ui;


import javax.swing.*;
import org.otopark.model.Customer;
import org.otopark.service.CustomerService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterForm {

    public RegisterForm() {
        // JFrame oluştur
        JFrame frame = new JFrame("Kayıt Formu");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Pencereyi kapatınca uygulama durmasın
        frame.setLayout(new GridBagLayout());

        // GridBagConstraints ayarları
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Bileşenler arası boşluk
        gbc.fill = GridBagConstraints.HORIZONTAL; // Bileşenlerin yatayda genişlemesini sağlar

        // Ad etiketi ve textfield
        JLabel nameLabel = new JLabel("Ad:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        JTextField nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(nameField, gbc);

        // Soyad etiketi ve textfield
        JLabel surnameLabel = new JLabel("Soyad:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(surnameLabel, gbc);

        JTextField surnameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(surnameField, gbc);

        // E-posta etiketi ve textfield
        JLabel emailLabel = new JLabel("E-posta:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(emailLabel, gbc);

        JTextField emailField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(emailField, gbc);

        // Telefon Numarası etiketi ve textfield
        JLabel phoneLabel = new JLabel("Telefon Numarası:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(phoneField, gbc);

        // Yaş etiketi ve textfield
        JLabel ageLabel = new JLabel("Yaş:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(ageLabel, gbc);

        JTextField ageField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(ageField, gbc);

        // Uyarı etiketi
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        frame.add(messageLabel, gbc);

        // Kayıt butonu
        JButton registerButton = new JButton("Kayıt Ol");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        frame.add(registerButton, gbc);

        // Buton tıklama işlemi
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                int age = Integer.parseInt(ageField.getText());

                // Verilerin doğruluğunu kontrol et
                if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    messageLabel.setText("Tüm alanları doldurduğunuzdan emin olun!");
                    return;
                }

                Customer customer = new Customer(name, surname, email, phone, age);

                try {
                    CustomerService userService = new CustomerService();
                    boolean success = userService.createCustomer(customer);

                    if (success) {
                        System.out.println("Kullanıcı başarıyla kaydedildi.");
                        frame.dispose();
                        
                    } else {
                        System.out.println("Kullanıcı kaydı başarısız.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(" Veritabani hatasi.");
                }
            }
        });

        // Pencereyi görünür hale getir
        frame.setLocationRelativeTo(null); // Ekranın ortasına yerleştir
        frame.setVisible(true); // Pencereyi görünür yap
    }
}