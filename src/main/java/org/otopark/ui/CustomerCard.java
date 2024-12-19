package org.otopark.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.otopark.model.Customer;

public class CustomerCard extends JFrame{


    public CustomerCard(Customer customer) {
     super("Müşteri Kartı");
    JDialog customerDialog = new JDialog();
    customerDialog.setTitle("Müşteri Bilgileri");
    customerDialog.setSize(400, 300);
    customerDialog.setLayout(new BorderLayout());
    customerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    // Card görünümü için panel
    JPanel cardPanel = new JPanel();
    cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
    cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Müşteri bilgileri
    JLabel nameLabel = new JLabel("Ad: " + customer.getName());
    JLabel surnameLabel = new JLabel("Soyad: " + customer.getSurname());
    JLabel emailLabel = new JLabel("Email: " + customer.getEmail());
    JLabel phoneLabel = new JLabel("Telefon: " + customer.getPhoneNumber());
    JLabel ageLabel = new JLabel("Yaş: " + customer.getAge());

    // Font ve boşluk ekleme
    Font font = new Font("Arial", Font.PLAIN, 16);
    nameLabel.setFont(font);
    surnameLabel.setFont(font);
    emailLabel.setFont(font);
    phoneLabel.setFont(font);
    ageLabel.setFont(font);

    nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    surnameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    emailLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    phoneLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
    ageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

    // Panele ekle
    cardPanel.add(nameLabel);
    cardPanel.add(surnameLabel);
    cardPanel.add(emailLabel);
    cardPanel.add(phoneLabel);
    cardPanel.add(ageLabel);

    customerDialog.add(cardPanel, BorderLayout.CENTER);

    // Görüntüle
    customerDialog.setVisible(true);

    }
}
