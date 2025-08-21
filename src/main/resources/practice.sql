-- 10 LEFT JOIN examples
-- 1. All passengers and their rides (even if no rides)
SELECT p.name, r.ride_id
FROM Passengers p
LEFT JOIN Rides r ON p.passenger_id = r.passenger_id;

-- 2. All drivers and their cars (even if no car assigned)
SELECT d.name, c.model
FROM Drivers d
LEFT JOIN Cars c ON d.driver_id = c.driver_id;

-- 3. All locations and rides starting there
SELECT l.address, r.ride_id
FROM Locations l
LEFT JOIN Rides r ON l.location_id = r.start_location_id;

-- 4. All pricing options and rides using them
SELECT p.base_fare, r.ride_id
FROM Pricing p
LEFT JOIN Rides r ON p.price_id = r.price_id;

-- 5. All passengers and their emergency contacts
SELECT p.name, e.name AS emergency_contact
FROM Passengers p
LEFT JOIN EmergencyContacts e ON p.passenger_id = e.passenger_id;

-- 6. All rides and their payments (including unpaid)
SELECT r.ride_id, p.amount
FROM Rides r
LEFT JOIN Payments p ON r.ride_id = p.ride_id;

-- 7. All drivers and their ratings
SELECT d.name, dr.rating
FROM Drivers d
LEFT JOIN DriverRatings dr ON d.driver_id = dr.driver_id;

-- 8. All promo codes and their usage
SELECT pc.code, upc.usage_date
FROM PromoCodes pc
LEFT JOIN UserPromoCodes upc ON pc.promo_id = upc.promo_id;

-- 9. All passengers and their support tickets
SELECT p.name, st.subject
FROM Passengers p
LEFT JOIN SupportTickets st ON p.passenger_id = st.passenger_id;

-- 10. All rides and their reviews
SELECT r.ride_id, rr.comment
FROM Rides r
LEFT JOIN RideReviews rr ON r.ride_id = rr.ride_id;

-- 10 RIGHT JOIN examples
-- 1. All rides and their passengers (including rides with no passenger)
SELECT r.ride_id, p.name
FROM Passengers p
RIGHT JOIN Rides r ON p.passenger_id = r.passenger_id;

-- 2. All cars and their drivers (including unassigned cars)
SELECT c.model, d.name
FROM Drivers d
RIGHT JOIN Cars c ON d.driver_id = c.driver_id;

-- 3. All rides and their end locations
SELECT r.ride_id, l.address
FROM Locations l
RIGHT JOIN Rides r ON l.location_id = r.end_location_id;

-- 4. All payments and their rides
SELECT p.payment_id, r.ride_id
FROM Rides r
RIGHT JOIN Payments p ON r.ride_id = p.ride_id;

-- 5. All emergency contacts and their passengers
SELECT e.name AS contact, p.name AS passenger
FROM Passengers p
RIGHT JOIN EmergencyContacts e ON p.passenger_id = e.passenger_id;

-- 6. All reviews and their rides
SELECT rr.rating, r.ride_id
FROM Rides r
RIGHT JOIN RideReviews rr ON r.ride_id = rr.ride_id;

-- 7. All used promo codes and their passengers
SELECT upc.usage_date, p.name
FROM Passengers p
RIGHT JOIN UserPromoCodes upc ON p.passenger_id = upc.passenger_id;

-- 8. All notifications and their users
SELECT n.message, p.name
FROM Passengers p
RIGHT JOIN Notifications n ON p.passenger_id = n.user_id;

-- 9. All support tickets and their passengers
SELECT st.subject, p.name
FROM Passengers p
RIGHT JOIN SupportTickets st ON p.passenger_id = st.passenger_id;

-- 10. All driver ratings and their drivers
SELECT dr.rating, d.name
FROM Drivers d
RIGHT JOIN DriverRatings dr ON d.driver_id = dr.driver_id;

-- 10 INNER JOIN examples
-- 1. Passengers with completed rides
SELECT p.name, r.ride_id
FROM Passengers p
INNER JOIN Rides r ON p.passenger_id = r.passenger_id
WHERE r.status = 'completed';

-- 2. Drivers with assigned cars
SELECT d.name, c.model
FROM Drivers d
INNER JOIN Cars c ON d.driver_id = c.driver_id;

-- 3. Rides with payment information
SELECT r.ride_id, p.amount
FROM Rides r
INNER JOIN Payments p ON r.ride_id = p.ride_id;

-- 4. Passengers with emergency contacts
SELECT p.name, e.phone
FROM Passengers p
INNER JOIN EmergencyContacts e ON p.passenger_id = e.passenger_id;

-- 5. Rides with reviews
SELECT r.ride_id, rr.rating
FROM Rides r
INNER JOIN RideReviews rr ON r.ride_id = rr.ride_id;

-- 6. Promo codes that have been used
SELECT pc.code, p.name
FROM PromoCodes pc
INNER JOIN UserPromoCodes upc ON pc.promo_id = upc.promo_id
INNER JOIN Passengers p ON upc.passenger_id = p.passenger_id;

-- 7. Drivers with ratings from passengers
SELECT d.name, dr.rating, p.name AS rated_by
FROM Drivers d
INNER JOIN DriverRatings dr ON d.driver_id = dr.driver_id
INNER JOIN Passengers p ON dr.passenger_id = p.passenger_id;

-- 8. Support tickets with passenger info
SELECT p.name, st.subject, st.status
FROM Passengers p
INNER JOIN SupportTickets st ON p.passenger_id = st.passenger_id;

-- 9. Notifications for specific users
SELECT p.name, n.message
FROM Passengers p
INNER JOIN Notifications n ON p.passenger_id = n.user_id;

-- 10. Rides with pricing details
SELECT r.ride_id, p.base_fare, p.per_km_rate
FROM Rides r
INNER JOIN Pricing p ON r.price_id = p.price_id;

-- 10 OUTER JOIN examples (MySQL uses LEFT/RIGHT JOIN + UNION)
-- 1. All passengers and all rides (full relationship)
SELECT p.name, r.ride_id
FROM Passengers p
LEFT JOIN Rides r ON p.passenger_id = r.passenger_id
UNION
SELECT p.name, r.ride_id
FROM Passengers p
RIGHT JOIN Rides r ON p.passenger_id = r.passenger_id
WHERE p.passenger_id IS NULL;

-- 2. All drivers and all cars
SELECT d.name, c.model
FROM Drivers d
LEFT JOIN Cars c ON d.driver_id = c.driver_id
UNION
SELECT d.name, c.model
FROM Drivers d
RIGHT JOIN Cars c ON d.driver_id = c.driver_id
WHERE d.driver_id IS NULL;

-- 3. All locations and all rides (start or end)
SELECT l.address, 'Start' AS point, r.ride_id
FROM Locations l
LEFT JOIN Rides r ON l.location_id = r.start_location_id
UNION
SELECT l.address, 'End' AS point, r.ride_id
FROM Locations l
LEFT JOIN Rides r ON l.location_id = r.end_location_id;

-- 4. All pricing and all rides
SELECT p.price_id, r.ride_id
FROM Pricing p
LEFT JOIN Rides r ON p.price_id = r.price_id
UNION
SELECT p.price_id, r.ride_id
FROM Pricing p
RIGHT JOIN Rides r ON p.price_id = r.price_id
WHERE p.price_id IS NULL;

-- 5. All passengers and all emergency contacts
SELECT p.name, e.name AS contact
FROM Passengers p
LEFT JOIN EmergencyContacts e ON p.passenger_id = e.passenger_id
UNION
SELECT p.name, e.name AS contact
FROM Passengers p
RIGHT JOIN EmergencyContacts e ON p.passenger_id = e.passenger_id
WHERE p.passenger_id IS NULL;

-- 6. All rides and all payments
SELECT r.ride_id, p.payment_id
FROM Rides r
LEFT JOIN Payments p ON r.ride_id = p.ride_id
UNION
SELECT r.ride_id, p.payment_id
FROM Rides r
RIGHT JOIN Payments p ON r.ride_id = p.ride_id
WHERE r.ride_id IS NULL;

-- 7. All promo codes and all usages
SELECT pc.code, upc.usage_date
FROM PromoCodes pc
LEFT JOIN UserPromoCodes upc ON pc.promo_id = upc.promo_id
UNION
SELECT pc.code, upc.usage_date
FROM PromoCodes pc
RIGHT JOIN UserPromoCodes upc ON pc.promo_id = upc.promo_id
WHERE pc.promo_id IS NULL;

-- 8. All drivers and all ratings
SELECT d.name, dr.rating
FROM Drivers d
LEFT JOIN DriverRatings dr ON d.driver_id = dr.driver_id
UNION
SELECT d.name, dr.rating
FROM Drivers d
RIGHT JOIN DriverRatings dr ON d.driver_id = dr.driver_id
WHERE d.driver_id IS NULL;

-- 9. All passengers and all support tickets
SELECT p.name, st.ticket_id
FROM Passengers p
LEFT JOIN SupportTickets st ON p.passenger_id = st.passenger_id
UNION
SELECT p.name, st.ticket_id
FROM Passengers p
RIGHT JOIN SupportTickets st ON p.passenger_id = st.passenger_id
WHERE p.passenger_id IS NULL;

-- 10. All rides and all reviews
SELECT r.ride_id, rr.review_id
FROM Rides r
LEFT JOIN RideReviews rr ON r.ride_id = rr.ride_id
UNION
SELECT r.ride_id, rr.review_id
FROM Rides r
RIGHT JOIN RideReviews rr ON r.ride_id = rr.ride_id
WHERE r.ride_id IS NULL;

-- Big query joining whole database
SELECT 
    p.name AS passenger,
    d.name AS driver,
    c.model AS car,
    start_loc.address AS pickup,
    end_loc.address AS dropoff,
    pr.base_fare,
    pr.per_km_rate,
    py.amount,
    py.payment_method,
    rr.rating AS ride_rating,
    rr.comment,
    dr.rating AS driver_rating,
    ec.name AS emergency_contact,
    pc.code AS promo_code,
    st.subject AS support_ticket,
    n.message AS notification
FROM Rides r
LEFT JOIN Passengers p ON r.passenger_id = p.passenger_id
LEFT JOIN Drivers d ON r.driver_id = d.driver_id
LEFT JOIN Cars c ON d.driver_id = c.driver_id
LEFT JOIN Locations start_loc ON r.start_location_id = start_loc.location_id
LEFT JOIN Locations end_loc ON r.end_location_id = end_loc.location_id
LEFT JOIN Pricing pr ON r.price_id = pr.price_id
LEFT JOIN Payments py ON r.ride_id = py.ride_id
LEFT JOIN RideReviews rr ON r.ride_id = rr.ride_id
LEFT JOIN DriverRatings dr ON d.driver_id = dr.driver_id AND p.passenger_id = dr.passenger_id
LEFT JOIN EmergencyContacts ec ON p.passenger_id = ec.passenger_id
LEFT JOIN UserPromoCodes upc ON p.passenger_id = upc.passenger_id
LEFT JOIN PromoCodes pc ON upc.promo_id = pc.promo_id
LEFT JOIN SupportTickets st ON p.passenger_id = st.passenger_id
LEFT JOIN Notifications n ON p.passenger_id = n.user_id;

-- 3 GROUP BY queries
-- 1. Count rides per passenger
SELECT p.name, COUNT(r.ride_id) AS ride_count
FROM Passengers p
LEFT JOIN Rides r ON p.passenger_id = r.passenger_id
GROUP BY p.name;

-- 2. Average rating per driver
SELECT d.name, AVG(dr.rating) AS avg_rating
FROM Drivers d
LEFT JOIN DriverRatings dr ON d.driver_id = dr.driver_id
GROUP BY d.name;

-- 3. Total payments per payment method
SELECT payment_method, SUM(amount) AS total_amount
FROM Payments
GROUP BY payment_method;

-- 3 HAVING queries
-- 1. Passengers with more than 1 ride
SELECT p.name, COUNT(r.ride_id) AS ride_count
FROM Passengers p
LEFT JOIN Rides r ON p.passenger_id = r.passenger_id
GROUP BY p.name
HAVING ride_count > 1;

-- 2. Drivers with average rating above 4.5
SELECT d.name, AVG(dr.rating) AS avg_rating
FROM Drivers d
LEFT JOIN DriverRatings dr ON d.driver_id = dr.driver_id
GROUP BY d.name
HAVING avg_rating > 4.5;

-- 3. Payment methods with total over $50
SELECT payment_method, SUM(amount) AS total_amount
FROM Payments
GROUP BY payment_method
HAVING total_amount > 50;

-- 10 queries with aggregate functions (added to existing queries)
-- 1. MIN ride distance
SELECT MIN(distance) AS shortest_ride FROM Rides;

-- 2. MAX ride distance
SELECT MAX(distance) AS longest_ride FROM Rides;

-- 3. AVG driver rating
SELECT AVG(rating) AS avg_driver_rating FROM Drivers;

-- 4. SUM of all payments
SELECT SUM(amount) AS total_payments FROM Payments;

-- 5. COUNT of completed rides
SELECT COUNT(*) AS completed_rides FROM Rides WHERE status = 'completed';

-- 6. AVG ride rating per passenger
SELECT p.name, AVG(rr.rating) AS avg_ride_rating
FROM Passengers p
LEFT JOIN Rides r ON p.passenger_id = r.passenger_id
LEFT JOIN RideReviews rr ON r.ride_id = rr.ride_id
GROUP BY p.name;

-- 7. MAX promo code discount
SELECT MAX(discount) AS max_discount FROM PromoCodes;

-- 8. SUM of distance traveled by each driver
SELECT d.name, SUM(r.distance) AS total_distance
FROM Drivers d
LEFT JOIN Rides r ON d.driver_id = r.driver_id
GROUP BY d.name;

-- 9. AVG payment amount per payment method
SELECT payment_method, AVG(amount) AS avg_payment
FROM Payments
GROUP BY payment_method;

-- 10. COUNT of support tickets per status
SELECT status, COUNT(*) AS ticket_count
FROM SupportTickets
GROUP BY status;