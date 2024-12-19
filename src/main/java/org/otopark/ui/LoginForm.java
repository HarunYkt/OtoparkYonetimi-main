package org.otopark.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;

import org.otopark.utils.DatabaseConnection;

public class LoginForm {


    public LoginForm() {
        // JFrame oluştur
        JFrame frame = new JFrame("Login Form");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        // GridBagConstraints ayarları
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Kullanıcı adı etiketi ve textfield
        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(usernameField, gbc);

        // Şifre etiketi ve passwordfield
        JLabel passwordLabel = new JLabel("Şifre:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(passwordField, gbc);

        // Uyarı etiketi
        JLabel messageLabel = new JLabel();
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(messageLabel, gbc);

        // Giriş butonu
        JButton loginButton = new JButton("Giriş Yap");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(loginButton, gbc);

        // Buton tıklama işlemi
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Kullanıcı girişi doğrulama
                if (validateLogin(username, password)) {
                    messageLabel.setText("");
                    JOptionPane.showMessageDialog(frame, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                    // MainPage açılır
                    new MainPage();
                    frame.dispose();
                } else {
                    messageLabel.setText("Kullanıcı adı veya şifre yanlış!");
                }
            }
        });

        // Frame görünür hale getir
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean validateLogin(String username, String password) {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Kullanıcı varsa true döner

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}