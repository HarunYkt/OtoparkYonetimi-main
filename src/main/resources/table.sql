CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);



CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    age INT
);


CREATE TABLE Parking (
    id INT PRIMARY KEY AUTO_INCREMENT,  
    name VARCHAR(255) NOT NULL          
);




CREATE TABLE vehicle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    plateNumber VARCHAR(15) NOT NULL UNIQUE,
    vehicleType VARCHAR(50) NOT NULL,
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL
);

CREATE TABLE ParkingSpot (
    id INT PRIMARY KEY AUTO_INCREMENT,     -- Park alanı ID'si (otomatik artan)
    spotNumber INT NOT NULL,               -- Park alanı numarası (boş olamaz)
    isOccupied BOOLEAN NOT NULL,           -- Park alanının doluluk durumu
    vehiclePlate VARCHAR(20),              -- Araç plaka numarası (maksimum 20 karakter)
    hourlyRate DECIMAL(10, 2)              -- Saatlik ücret (örneğin, 99999999.99 formatında)
);


CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,           
    amount DECIMAL(10, 2) NOT NULL,              
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
    payment_method VARCHAR(50) NOT NULL,         
    parking_spot_id INT NOT NULL,                
    FOREIGN KEY (parking_spot_id) REFERENCES ParkingSpot(id) 
    ON DELETE CASCADE
);