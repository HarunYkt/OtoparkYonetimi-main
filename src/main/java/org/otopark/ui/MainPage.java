package org.otopark.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.otopark.dao.ParkingDao;
import org.otopark.model.Customer;
import org.otopark.model.ParkingSpot;
import org.otopark.service.CustomerService;
import org.otopark.service.ParkingSpotService;
import org.otopark.service.PaymentService;
import org.otopark.service.VehicleService;

public class MainPage {

    // yapilacaklar ekranda slotlari tiklayinca plakalari listele ve o spoat alana
    // ata

    private List<ParkingSpot> parkingSpots;
    private List<JButton> spotButtons;
    private ParkingSpotService parkingSpotService;

    public MainPage() {



        // Örnek otopark alanlarını oluştur
        parkingSpots = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            parkingSpots.add(new ParkingSpot(i, i, false, null, 10.0));

        }

        // Ana pencere
        JFrame frame = new JFrame("Otopark Yönetim Sistemi");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Menü çubuğu
        JMenuBar menuBar = new JMenuBar();

        // Otopark Menüsü
        JMenu parkingMenu = new JMenu("Otopark Yönetimi");
        JMenuItem addParkingItem = new JMenuItem("Yeni Park Ekle");
        JMenuItem searchByPlateItem = new JMenuItem("Plaka ile Ara");
        JMenuItem listAllVehiclesItem = new JMenuItem("Tüm Araçları Listele");
        JMenuItem addNewVehicle = new JMenuItem("Sisteme Yeni Arac Ekle");
        parkingMenu.add(addParkingItem);
        parkingMenu.add(searchByPlateItem);
        parkingMenu.add(listAllVehiclesItem);
        parkingMenu.add(addNewVehicle);

        listAllVehiclesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    VehicleService vehicleService = new VehicleService();
                    new VehicleTable(vehicleService.getAllVehicles());

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addNewVehicle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VehicleForm();
            }
        });

        // Müşteri Menüsü
        JMenu customerMenu = new JMenu("Müşteri Yönetimi");
        JMenuItem addCustomerItem = new JMenuItem("Yeni Müşteri Ekle");
        JMenuItem listCustomer = new JMenuItem("Tum Musteriler");
        JMenuItem searchByPhoneItem = new JMenuItem("Cep No ile Ara");
        JMenuItem applyDiscountItem = new JMenuItem("İndirim Uygula");
        customerMenu.add(addCustomerItem);
        customerMenu.add(searchByPhoneItem);
        customerMenu.add(applyDiscountItem);
        customerMenu.add(listCustomer);

        searchByPhoneItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JDialog searchDialog = new JDialog();
                searchDialog.setTitle("Telefon Numarası ile Ara");
                searchDialog.setSize(400, 200);
                searchDialog.setLayout(new BorderLayout());
                searchDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                // Panel ve bileşenler
                JPanel inputPanel = new JPanel(new FlowLayout());
                JLabel phoneLabel = new JLabel("Telefon Numarası:");
                JTextField phoneField = new JTextField(20);
                JButton searchButton = new JButton("Ara");

                inputPanel.add(phoneLabel);
                inputPanel.add(phoneField);
                searchDialog.add(inputPanel, BorderLayout.CENTER);
                searchDialog.add(searchButton, BorderLayout.SOUTH);

                // Arama butonu işlevi
                searchButton.addActionListener(ev -> {
                    String phoneNumber = phoneField.getText().trim();
                    if (phoneNumber.isEmpty()) {
                        JOptionPane.showMessageDialog(searchDialog, "Lütfen bir telefon numarası girin!", "Hata",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    CustomerService customerService = null;
                    try {
                        customerService = new CustomerService();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    // Müşteriyi bul
                    Customer customer = customerService.getCustomerByPhone(phoneNumber);
                    if (customer != null) {
                        // Müşteri bilgilerini göster
                        new CustomerCard(customer);
                        searchDialog.dispose(); // Arama penceresini kapat
                    } else {
                        // Hata mesajı göster
                        JOptionPane.showMessageDialog(searchDialog, "Bu telefon numarasına ait müşteri bulunamadı.",
                                "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                });

                searchDialog.setVisible(true);

            }
        });
        listCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    CustomerService customerService = new CustomerService();
                    new CustomersTable(customerService.getAllCustomers());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        addCustomerItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterForm();
            }
        });

        // Ödeme Menüsü
        JMenu paymentMenu = new JMenu("Ödeme Yönetimi");
        JMenuItem paymentStatusItem = new JMenuItem("Ödeme Durumları");
        JMenuItem makePaymentItem = new JMenuItem("Ödeme Al");
        paymentMenu.add(paymentStatusItem);
        paymentMenu.add(makePaymentItem);

        //Sonradan eklendi
        //PaymentForm

        makePaymentItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PaymentForm();
            }
        });

        paymentStatusItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    PaymentService paymentService = new PaymentService();
                    new PaymentTable(paymentService.getAllPayments());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });




        // Çıkış Menüsü
        JMenu exitMenu = new JMenu("Çıkış");
        JMenuItem logoutItem = new JMenuItem("Çıkış Yap");
        exitMenu.add(logoutItem);

        // Menü çubuğuna ekleme
        menuBar.add(parkingMenu);
        menuBar.add(customerMenu);
        menuBar.add(paymentMenu);
        menuBar.add(exitMenu);
        frame.setJMenuBar(menuBar);

        // Otopark alanı görselleştirme paneli slot yonetimi
        JPanel parkingPanel = new JPanel();
        parkingPanel.setLayout(new GridLayout(4, 5, 10, 10)); // 4 satır, 5 sütun

        List<JButton> spotButtons = new ArrayList<>();

        for (ParkingSpot spot : parkingSpots) {
            JButton spotButton = new JButton("Slot " + spot.getSpotNumber());
            spotButton.setBackground(spot.isOccupied() ? Color.RED : Color.GREEN);
            spotButton.setOpaque(true);
            spotButton.setBorderPainted(false);

            spotButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        new ParkingSpotForm(spot).setVisible(true);
                        List<ParkingSpot> spotInDb = new ParkingSpotService().getAllParkingSpots();

                        for (int i = 0; i < spotInDb.size(); i++) {
                            JButton spotButton = spotButtons.get(i);
                
                            // Eğer dolu ise kırmızı, boşsa yeşil yap
                            spotButton.setBackground(spotInDb.get(i).isOccupied() ? Color.RED : Color.GREEN);
                            spotButton.setOpaque(true);
                            spotButton.setBorderPainted(false);
                            //bunu bir method haline getir 
                        }

                    } catch (SQLException e1) {

                        e1.printStackTrace();
                    }
                }

        });

            spotButtons.add(spotButton);
            parkingPanel.add(spotButton);
    }

        // Ana çerçeveye panelleri ekle
        frame.add(parkingPanel, BorderLayout.CENTER);

        searchByPlateItem.addActionListener(e -> {
            String plate = JOptionPane.showInputDialog(frame, "Plaka girin:", "Ara", JOptionPane.QUESTION_MESSAGE);
            if (plate != null && !plate.trim().isEmpty()) {
                boolean found = false;
                for (ParkingSpot spot : parkingSpots) {
                    if (plate.equals(spot.getVehiclePlate())) {
                        JOptionPane.showMessageDialog(frame, "Araç bulundu: Slot " + spot.getSpotNumber(), "Sonuç",
                                JOptionPane.INFORMATION_MESSAGE);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(frame, "Araç bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Çıkış yapılıyor...", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        });

        // Çerçeveyi görünür hale getir
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}