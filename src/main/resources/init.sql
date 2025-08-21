-- Database creation
DROP DATABASE IF EXISTS taxi;
CREATE DATABASE taxi;
USE taxi;

-- 1. Passengers
CREATE TABLE Passengers (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(50) UNIQUE,
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. Drivers
CREATE TABLE Drivers (
    driver_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    license_number VARCHAR(30) UNIQUE,
    rating DECIMAL(3, 2) DEFAULT 5.0
);

-- 3. CarTypes
CREATE TABLE CarTypes (
    type_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description TEXT
);

-- 4. Cars
CREATE TABLE Cars (
    car_id INT PRIMARY KEY AUTO_INCREMENT,
    driver_id INT UNIQUE,
    model VARCHAR(50),
    plate_number VARCHAR(15) UNIQUE,
    type_id INT,
    FOREIGN KEY (driver_id) REFERENCES Drivers(driver_id),
    FOREIGN KEY (type_id) REFERENCES CarTypes(type_id)
);

-- 5. Locations
CREATE TABLE Locations (
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    address VARCHAR(100) NOT NULL,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8)
);

-- 6. Pricing
CREATE TABLE Pricing (
    price_id INT PRIMARY KEY AUTO_INCREMENT,
    base_fare DECIMAL(10, 2),
    per_km_rate DECIMAL(10, 2),
    surge_multiplier DECIMAL(3, 2) DEFAULT 1.0
);

-- 7. Rides
CREATE TABLE Rides (
    ride_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    driver_id INT,
    start_location_id INT,
    end_location_id INT,
    price_id INT,
    start_time DATETIME,
    end_time DATETIME,
    status ENUM('pending', 'ongoing', 'completed', 'cancelled'),
    distance DECIMAL(10, 2),
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id),
    FOREIGN KEY (driver_id) REFERENCES Drivers(driver_id),
    FOREIGN KEY (start_location_id) REFERENCES Locations(location_id),
    FOREIGN KEY (end_location_id) REFERENCES Locations(location_id),
    FOREIGN KEY (price_id) REFERENCES Pricing(price_id)
);

-- 8. Payments
CREATE TABLE Payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    ride_id INT UNIQUE,
    amount DECIMAL(10, 2),
    payment_method ENUM('cash', 'card', 'apple_pay', 'google_pay'),
    FOREIGN KEY (ride_id) REFERENCES Rides(ride_id)
);

-- 9. RideReviews
CREATE TABLE RideReviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    ride_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    FOREIGN KEY (ride_id) REFERENCES Rides(ride_id)
);

-- 10. PromoCodes
CREATE TABLE PromoCodes (
    promo_id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) UNIQUE,
    discount DECIMAL(5, 2),
    expiry_date DATE
);

-- 11. UserPromoCodes
CREATE TABLE UserPromoCodes (
    user_promo_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    promo_id INT,
    usage_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id),
    FOREIGN KEY (promo_id) REFERENCES PromoCodes(promo_id)
);

-- 12. EmergencyContacts
CREATE TABLE EmergencyContacts (
    contact_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT UNIQUE,
    name VARCHAR(50),
    phone VARCHAR(20),
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id)
);

-- 13. SupportTickets
CREATE TABLE SupportTickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    subject VARCHAR(100),
    message TEXT,
    status ENUM('open', 'resolved'),
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id)
);

-- 14. Notifications
CREATE TABLE Notifications (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES Passengers(passenger_id)
);

-- 15. DriverRatings
CREATE TABLE DriverRatings (
    rating_id INT PRIMARY KEY AUTO_INCREMENT,
    driver_id INT,
    passenger_id INT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    FOREIGN KEY (driver_id) REFERENCES Drivers(driver_id),
    FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id)
);

-- Insert data
INSERT INTO Passengers (name, phone, email) VALUES
('Ivan Petrenko', '+380501234567', 'ivan@example.com'),
('Olena Kovalenko', '+380671234567', 'olena@example.com'),
('Mykola Shevchenko', '+380631234567', 'mykola@example.com'),
('Tetiana Boyko', '+380501112283', 'tetiana@example.com'),
('Andriy Melnyk', '+380672223344', 'andriy@example.com'),
('Nataliya Kovalchuk', '+380633334455', 'nataliya@example.com'),
('Vasyl Bondarenko', '+380504445566', 'vasyl@example.com'),
('Hanna Tkachenko', '+380675556677', 'hanna@example.com'),
('Oleksiy Savchenko', '+380636667788', 'oleksiy@example.com'),
('Yulia Polishchuk', '+380507778899', 'yulia@example.com');

INSERT INTO Drivers (name, license_number, rating) VALUES
('Petro Ivanenko', 'UA123456', 4.75),
('Mykhailo Sydorenko', 'UA654321', 4.90),
('Volodymyr Pavlenko', 'UA987654', 4.50),
('Serhiy Kovalchuk', 'UA111222', 4.80),
('Vitaliy Shevchuk', 'UA333444', 4.95),
('Oleksandr Bondar', 'UA555666', 4.60),
('Yuriy Tkachenko', 'UA777888', 4.85),
('Dmytro Savchuk', 'UA999000', 4.70),
('Ihor Melnychuk', 'UA222333', 4.90),
('Bohdan Polishchuk', 'UA444555', 4.65);

INSERT INTO CarTypes (name, description) VALUES
('Economy', 'Standard car with basic features'),
('Comfort', 'More spacious and comfortable'),
('Business', 'Premium cars with extra services');

INSERT INTO Cars (driver_id, model, plate_number, type_id) VALUES
(1, 'Toyota Camry', 'AA1234BB', 1),
(2, 'Honda Accord', 'BC5678CE', 2),
(3, 'BMW 5 Series', 'AI9012KA', 3),
(4, 'Hyundai Elantra', 'KA3456AX', 1),
(5, 'Kia Optima', 'BH7890HT', 2),
(6, 'Mercedes E-Class', 'AA4321BB', 3),
(7, 'Toyota Corolla', 'BC8765CE', 1),
(8, 'Volkswagen Passat', 'AI2109KA', 2),
(9, 'Audi A6', 'KA6543AX', 3),
(10, 'Nissan Altima', 'BH0987HT', 1);

INSERT INTO Locations (address, latitude, longitude) VALUES
('Khreshchatyk St, 1, Kyiv', 50.450033, 30.524136),
('Soborna Sq, 5, Lviv', 49.842957, 24.031111),
('Deribasivska St, 25, Odesa', 46.485722, 30.743888),
('Pushkinska St, 37, Kharkiv', 49.993500, 36.230383),
('Ploshcha Rynok, 10, Ivano-Frankivsk', 48.922633, 24.711117),
('Dnistrovska Naberezhna, 2, Chernivtsi', 48.291683, 25.935469),
('Prospekt Svobody, 28, Ternopil', 49.553517, 25.594767),
('Vulytsya Soborna, 3, Uzhhorod', 48.620800, 22.287883),
('Vulytsya Hretska, 18, Poltava', 49.588267, 34.551417),
('Vulytsya Dmytra Yavornytskoho, 106, Dnipro', 48.464717, 35.046183);

INSERT INTO Pricing (base_fare, per_km_rate, surge_multiplier) VALUES
(5.00, 1.50, 1.0),
(7.00, 2.00, 1.2),
(10.00, 3.00, 1.5);

INSERT INTO Rides (passenger_id, driver_id, start_location_id, end_location_id, price_id, start_time, end_time, status, distance) VALUES
(1, 1, 1, 2, 1, '2025-08-01 08:00:00', '2025-08-01 08:30:00', 'completed', 10.5),
(2, 2, 2, 3, 2, '2025-08-02 09:00:00', '2025-08-02 09:45:00', 'completed', 15.2),
(3, 3, 3, 1, 3, '2025-08-03 10:00:00', NULL, 'ongoing', 8.7),
(4, 4, 4, 5, 1, '2025-08-04 11:00:00', '2025-08-04 11:25:00', 'completed', 7.3),
(5, 5, 5, 6, 2, '2025-08-05 12:00:00', '2025-08-05 12:40:00', 'completed', 12.1),
(6, 6, 6, 7, 3, '2025-08-06 13:00:00', '2025-08-06 13:30:00', 'completed', 9.8),
(7, 7, 7, 4, 1, '2025-08-07 14:00:00', NULL, 'ongoing', 5.5),
(8, 8, 4, 6, 2, '2025-08-08 15:00:00', '2025-08-08 15:45:00', 'completed', 14.2),
(9, 9, 5, 7, 3, '2025-08-09 16:00:00', NULL, 'cancelled', 3.7),
(10, 10, 6, 4, 1, '2025-08-10 17:00:00', '2025-08-10 17:20:00', 'completed', 6.9);

INSERT INTO Payments (ride_id, amount, payment_method) VALUES
(1, 20.75, 'card'),
(2, 37.40, 'apple_pay'),
(3, 18.25, 'google_pay'),
(4, 42.30, 'card'),
(5, 28.75, 'apple_pay'),
(6, 15.60, 'cash'),
(7, 33.90, 'card'),
(8, 12.45, 'google_pay'),
(9, 24.80, 'apple_pay');

INSERT INTO RideReviews (ride_id, rating, comment) VALUES
(1, 5, 'Great ride!'),
(2, 4, 'Good service'),
(3, 5, 'Excellent service!'),
(4, 4, 'Good driver, clean car'),
(5, 3, 'Late arrival but okay'),
(6, 5, 'Perfect ride'),
(7, 2, 'Route was not optimal'),
(8, 4, 'Friendly driver'),
(9, 5, 'Will use again!');

INSERT INTO PromoCodes (code, discount, expiry_date) VALUES
('WELCOME10', 10.00, '2025-08-31'),
('SUMMER20', 20.00, '2025-08-31'),
('AUGUST15', 15.00, '2025-08-31'),
('KYIV25', 25.00, '2025-08-15'),
('LVIV20', 20.00, '2025-08-20'),
('ODESA10', 10.00, '2025-08-10'),
('UKRAINE30', 30.00, '2025-08-24'),
('TAXI5', 5.00, '2025-08-31'),
('RIDE15', 15.00, '2025-08-31'),
('FAST10', 10.00, '2025-08-31');

INSERT INTO UserPromoCodes (passenger_id, promo_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9);

INSERT INTO EmergencyContacts (passenger_id, name, phone) VALUES
(1, 'Mariya Petrenko', '+380501112233'),
(2, 'Oleksandr Kovalenko', '+380671112233'),
(3, 'Viktoria Shevchenko', '+380631112233'),
(4, 'Yaroslav Boyko', '+380502223344'),
(5, 'Sofiya Melnyk', '+380672223344'),
(6, 'Artem Kovalchuk', '+380632223344'),
(7, 'Anastasia Bondarenko', '+380503334455'),
(8, 'Roman Tkachenko', '+380673334455'),
(9, 'Alina Savchenko', '+380633334455'),
(10, 'Pavlo Polishchuk', '+380504445566');

INSERT INTO SupportTickets (passenger_id, subject, message, status) VALUES
(1, 'Lost item', 'I left my phone in the car', 'open'),
(2, 'Payment issue', 'Double charged for my ride', 'resolved'),
(3, 'Refund request', 'Need refund for cancelled ride', 'open'),
(4, 'Driver complaint', 'Driver was rude', 'open'),
(5, 'App issue', 'App crashes on payment screen', 'resolved'),
(6, 'Account problem', 'Cannot login to my account', 'open'),
(7, 'Payment question', 'Where is my receipt?', 'resolved'),
(8, 'Rating issue', 'Cannot submit rating', 'open'),
(9, 'Promo code problem', 'Code not working', 'resolved');

INSERT INTO Notifications (user_id, message) VALUES
(1, 'Your ride has been completed'),
(2, 'New promo code available'),
(3, 'Your driver is arriving soon'),
(4, 'Payment processed successfully'),
(5, 'New features available in app'),
(6, 'Your ride has been completed'),
(7, 'Promo code expired'),
(8, 'Account password changed'),
(9, 'New promo code available for you');

INSERT INTO DriverRatings (driver_id, passenger_id, rating) VALUES
(1, 1, 5),
(2, 2, 4),
(3, 3, 5),
(4, 4, 5),
(5, 5, 4),
(6, 6, 3),
(7, 7, 5),
(8, 8, 4),
(9, 9, 5),
(10, 10, 4);