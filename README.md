# Otopark Yönetim Sistemi

Bu proje, bir otopark yönetim sistemini simüle etmek ve kullanıcıların otopark alanlarını yönetmesini sağlamak için geliştirilmiştir. Sistem; müşteri, araç, ödeme ve park alanı yönetimini içermektedir.

## Proje Özellikleri

- **Park Alanı Yönetimi:**
  - Boş/dolu park alanlarını görüntüleme
  - Araç giriş/çıkış işlemleri
  - Park alanı ücretlerini düzenleme

- **Müşteri Yönetimi:**
  - Yeni müşteri ekleme
  - Müşteri listesini görüntüleme
  - Telefon numarası ile müşteri arama

- **Araç Yönetimi:**
  - Yeni araç ekleme
  - Kayıtlı araçların listesini görüntüleme

- **Ödeme Yönetimi:**
  - Ödeme alma işlemleri
  - Ödeme geçmişini görüntüleme

## Teknolojiler ve Kütüphaneler

- **Java** (Ana programlama dili)
- **Swing** (Kullanıcı arayüzü için)
- **MySQL** (Veritabanı yönetimi)
- **JDBC** (Java Veritabanı Bağlantısı)
- **Lombok** (Kodun sadeleştirilmesi için)

## Dosya ve Paket Yapısı

- `org.otopark.ui`
  - Kullanıcı arayüzü ile ilgili sınıflar (MainPage, ParkingSpotForm vb.)
- `org.otopark.model`
  - Veritabanı modelleri (Customer, ParkingSpot, Vehicle, Payment vb.)
- `org.otopark.service`
  - İş mantığını içeren servis sınıfları
- `org.otopark.dao`
  - Veritabanı işlemleri için DAO sınıfları
- `org.otopark.utils`
  - Yardımcı sınıflar (DatabaseConnection vb.)

## Kurulum Talimatları

1. **Gerekli Bağımlılıkları Yükleyin:**
   - Java Development Kit (JDK)
   - MySQL Veritabanı
   - Maven (Opsiyonel, bağımlılık yönetimi için)

2. **Veritabanını Ayarlayın:**
   - Aşağıdaki SQL komutlarını kullanarak veritabanını oluşturun:
     ```sql
     CREATE DATABASE otopark_db;
     USE otopark_db;

     CREATE TABLE ParkingSpot (
         id INT AUTO_INCREMENT PRIMARY KEY,
         spotNumber INT NOT NULL,
         isOccupied BOOLEAN NOT NULL,
         vehiclePlate VARCHAR(20),
         hourlyRate DECIMAL(10, 2)
     );

     CREATE TABLE Customer (
         id INT AUTO_INCREMENT PRIMARY KEY,
         name VARCHAR(100),
         surname VARCHAR(100),
         email VARCHAR(255) UNIQUE,
         phoneNumber VARCHAR(20),
         age INT
     );

     CREATE TABLE Vehicle (
         id INT AUTO_INCREMENT PRIMARY KEY,
         plateNumber VARCHAR(15) UNIQUE,
         vehicleType VARCHAR(50),
         customerId INT,
         FOREIGN KEY (customerId) REFERENCES Customer(id) ON DELETE SET NULL
     );

     CREATE TABLE Payment (
         id INT AUTO_INCREMENT PRIMARY KEY,
         amount DECIMAL(10, 2),
         paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
         parkingSpotId INT,
         FOREIGN KEY (parkingSpotId) REFERENCES ParkingSpot(id) ON DELETE CASCADE
     );
     ```

3. **Projeyi Çalıştırın:**
   - Projeyi bir IDE (ör. IntelliJ IDEA veya Eclipse) kullanarak açın.
   - `MainPage` sınıfını çalıştırarak uygulamayı başlatın.

## Kullanım Talimatları

1. **Park Alanları Yönetimi:**
   - Ana ekranda yeşil olan alanlar boş, kırmızı olan alanlar dolu alanları temsil eder.
   - Bir alan üzerine tıklayarak araç bilgilerini girin veya çıkış işlemini gerçekleştirin.

2. **Müşteri ve Araç Yönetimi:**
   - Menüden "Müşteri Yönetimi" veya "Araç Yönetimi" seçeneklerini kullanarak ilgili işlemleri gerçekleştirin.

3. **Ödeme İşlemleri:**
   - Menüden "Ödeme Yönetimi" seçeneğini kullanarak ödeme alma ve geçmiş işlemleri görüntüleyin.


