package com.solvd.taxi;

import com.solvd.taxi.model.*;
import com.solvd.taxi.service.impl.*;
import com.solvd.taxi.service.mybatisimpl.*;
import com.solvd.taxi.service.interfaces.*;
import com.solvd.taxi.connection.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private final XmlService xmlService;
    private final PassengerService passengerService;
    private final DriverService driverService;
    private final RideService rideService;
    private final PaymentService paymentService;
    private final AnalyticsService analyticsService;
    private final JsonService jsonService;

    public Main() {
        //this.passengerService = new PassengerServiceImpl();
        //this.driverService = new DriverServiceImpl();
        //this.rideService = new RideServiceImpl();
        //this.paymentService = new PaymentServiceImpl();
        //this.analyticsService = new AnalyticsServiceImpl();
        this.passengerService = new PassengerServiceImplMyBatis();
        this.driverService = new DriverServiceImplMyBatis();
        this.rideService = new RideServiceImplMyBatis();
        this.paymentService = new PaymentServiceImplMyBatis();
        this.analyticsService = new AnalyticsServiceImplMyBatis();

        this.xmlService = new XmlServiceImpl();
        this.jsonService = new JsonServiceImpl();

        logger.info("Services initialized successfully");
    }

    public static void main(String[] args) {
        logger.info("Starting Taxi Service Application");

        try {
            Main app = new Main();
            app.runDemo();

            logger.info("Application completed successfully");
        } catch (Exception e) {
            logger.error("Application failed with error: {}", e.getMessage(), e);
        } finally {
            shutdown();
        }
    }

    private void runDemo() {
        logger.debug("Running taxi service demonstration");

        try {
            // Scenario simulation
            simulateRealWorldScenario();

            // Additional demonstrations of all services
            demonstrateAllServices();

        } catch (Exception e) {
            logger.error("Demo execution failed: {}", e.getMessage(), e);
        }
    }

    private void simulateRealWorldScenario() throws SQLException {
        logger.info("=== Simulating Taxi Service Scenario ===");

        // Customer registration and ride booking
        Passenger customer = registerNewCustomer();

        // Driver assignment and ride execution
        Driver assignedDriver = findAvailableDriver();

        // Ride creation and processing
        Ride customerRide = createAndProcessRide(customer, assignedDriver);

        // Payment processing
        processRidePayment(customerRide);

        // Post-ride analytics
        generatePostRideAnalytics();
    }

    private Passenger registerNewCustomer() throws SQLException {
        logger.info("--- Customer Registration ---");

        // Check if customer already exists
        Passenger existingCustomer = passengerService.findPassengerByPhone("+380501234567");
        if (existingCustomer != null) {
            logger.info("Existing customer found: {}", existingCustomer.getName());
            return existingCustomer;
        }

        // Register new customer
        Passenger newCustomer = new Passenger();
        newCustomer.setName("John Smith");
        newCustomer.setEmail("john.smith@email.com");
        newCustomer.setPhone("+380501234567");

        Passenger registeredCustomer = passengerService.registerPassenger(newCustomer);
        logger.info("New customer registered: {}", registeredCustomer.getName());

        return registeredCustomer;
    }

    private Driver findAvailableDriver() throws SQLException {
        logger.info("--- Finding Available Driver ---");

        // Get all available drivers
        List<Driver> availableDrivers = driverService.getAllDrivers();
        if (availableDrivers.isEmpty()) {
            throw new RuntimeException("No drivers available");
        }

        // Select driver with best rating
        Driver bestDriver = availableDrivers.stream()
                .filter(driver -> driver.getRating() >= 4.0)
                .findFirst()
                .orElse(availableDrivers.get(0));

        logger.info("Driver assigned: {} (Rating: {})", bestDriver.getName(), bestDriver.getRating());
        return bestDriver;
    }

    private Ride createAndProcessRide(Passenger passenger, Driver driver) throws SQLException {
        logger.info("--- Ride Creation and Processing ---");

        // Create new ride
        Ride ride = new Ride();
        ride.setPassengerId(passenger.getPassengerId());
        ride.setDriverId(driver.getDriverId());
        ride.setStartLocationId(1);  // Downtown
        ride.setEndLocationId(3);    // Airport
        ride.setPriceId(1);
        ride.setDistance(25.0);      // 25 km to airport

        Ride createdRide = rideService.createRide(ride);
        logger.info("Ride created: ID {}", createdRide.getRideId());

        // Simulate ride process
        rideService.startRide(createdRide.getRideId());
        logger.info("Ride started - driver is on the way");

        // Simulate ride completion
        rideService.completeRide(createdRide.getRideId());
        logger.info("Ride completed successfully");

        // Calculate final price
        double finalPrice = rideService.calculateRidePrice(createdRide.getRideId());
        logger.info("Final ride price: ${}", finalPrice);

        return createdRide;
    }

    private void processRidePayment(Ride ride) throws SQLException {
        logger.info("--- Payment Processing ---");

        // Create payment for the ride
        Payment payment = new Payment();
        payment.setRideId(ride.getRideId()); // Use the actual ride ID
        payment.setAmount(rideService.calculateRidePrice(ride.getRideId()));
        payment.setPaymentMethod("card");

        Payment processedPayment = paymentService.processPayment(payment);
        logger.info("Payment processed: ${} via {}",
                processedPayment.getAmount(),
                processedPayment.getPaymentMethod());

        // Verify payment was recorded
        Payment verifiedPayment = paymentService.getPaymentByRideId(ride.getRideId());
        if (verifiedPayment != null) {
            logger.info("Payment verification: SUCCESS - Payment ID {}", verifiedPayment.getPaymentId());
        } else {
            logger.warn("Payment verification: FAILED - No payment found for ride ID {}", ride.getRideId());
        }
    }

    private void generatePostRideAnalytics() throws SQLException {
        logger.info("--- Post-Ride Analytics ---");

        // Get overall ride statistics
        Map<String, Integer> rideStats = analyticsService.getRidesStatistics();
        logger.info("Total rides today: {}", rideStats.getOrDefault("today", 0));

        // Revenue analysis
        Map<String, Double> revenueStats = analyticsService.getRevenueStatistics();
        logger.info("Today's revenue: ${}", revenueStats.getOrDefault("today", 0.0));

        // Driver performance
        Map<String, Integer> driverStats = analyticsService.getDriverPerformanceStats();
        logger.info("Active drivers: {}", driverStats.size());
    }

    private void demonstrateAllServices() {
        logger.info("=== Comprehensive Service Demonstration ===");

        try {
            demonstratePassengerOperations();
            demonstrateDriverOperations();
            demonstrateRideOperations();
            demonstratePaymentOperations();
            demonstrateAnalytics();
            demonstrateXmlOperations();
            demonstrateJsonOperations();
        } catch (Exception e) {
            logger.error("Service demonstration failed: {}", e.getMessage(), e);
        }
    }

    private void demonstratePassengerOperations() throws SQLException {
        logger.info("--- Passenger Service Methods ---");

        // Demonstrates finding passenger by phone number
        Passenger passenger = passengerService.findPassengerByPhone("+380501234567");
        if (passenger != null) {
            logger.info("Found passenger: {} - {}", passenger.getName(), passenger.getEmail());
        }

        // Demonstrates finding passenger by email
        Passenger byEmail = passengerService.findPassengerByEmail("ivan@example.com");
        if (byEmail != null) {
            logger.info("Found passenger by email: {}", byEmail.getName());
        }

        // Demonstrates searching passengers by name pattern
        List<Passenger> searchResults = passengerService.searchPassengersByName("Ivan");
        logger.info("Found {} passengers matching 'Ivan'", searchResults.size());

        // Demonstrates getting all passengers
        List<Passenger> allPassengers = passengerService.getAllPassengers();
        logger.info("Total passengers in system: {}", allPassengers.size());

        // Demonstrates passenger count
        int totalCount = passengerService.getTotalPassengersCount();
        logger.info("Total passenger count: {}", totalCount);
    }

    private void demonstrateDriverOperations() throws SQLException {
        logger.info("--- Driver Service Methods ---");

        // Demonstrates finding driver by license number
        Driver driver = driverService.findDriverByLicense("UA123456");
        if (driver != null) {
            logger.info("Found driver: {} - Rating: {}", driver.getName(), driver.getRating());

            // Demonstrates updating driver rating
            boolean updated = driverService.updateDriverRating(driver.getDriverId(), 4.8);
            logger.info("Driver rating updated: {}", updated);
        }

        // Demonstrates getting all drivers
        List<Driver> allDrivers = driverService.getAllDrivers();
        logger.info("Total drivers available: {}", allDrivers.size());
    }

    private void demonstrateRideOperations() throws SQLException {
        logger.info("--- Ride Service Methods ---");

        // Demonstrates creating a new ride
        Ride ride = new Ride();
        ride.setPassengerId(1);
        ride.setDriverId(1);
        ride.setStartLocationId(2);
        ride.setEndLocationId(4);
        ride.setPriceId(1);
        ride.setDistance(15.0);

        Ride createdRide = rideService.createRide(ride);
        logger.info("Created ride with ID: {}", createdRide.getRideId());

        // Demonstrates starting a ride
        boolean started = rideService.startRide(createdRide.getRideId());
        logger.info("Ride started: {}", started);

        // Demonstrates completing a ride
        boolean completed = rideService.completeRide(createdRide.getRideId());
        logger.info("Ride completed: {}", completed);

        // Demonstrates getting passenger ride history
        List<Ride> passengerRides = rideService.getRidesByPassenger(1);
        logger.info("Passenger has {} previous rides", passengerRides.size());

        // Demonstrates price calculation
        double price = rideService.calculateRidePrice(createdRide.getRideId());
        logger.info("Calculated ride price: ${}", price);
    }

    private void demonstratePaymentOperations() throws SQLException {
        logger.info("--- Payment Service Methods ---");

        // First create a new ride for payment demonstration
        Ride demoRide = new Ride();
        demoRide.setPassengerId(1);
        demoRide.setDriverId(1);
        demoRide.setStartLocationId(3);
        demoRide.setEndLocationId(5);
        demoRide.setPriceId(1);
        demoRide.setDistance(10.0);

        Ride createdDemoRide = rideService.createRide(demoRide);
        logger.info("Created demo ride with ID: {}", createdDemoRide.getRideId());

        // Demonstrates payment processing for the new ride
        Payment payment = new Payment();
        payment.setRideId(createdDemoRide.getRideId()); // Use the newly created ride ID
        payment.setAmount(18.50);
        payment.setPaymentMethod("cash");

        Payment processedPayment = paymentService.processPayment(payment);
        logger.info("Payment processed: ID {}", processedPayment.getPaymentId());

        // Demonstrates retrieving payment by ride ID
        Payment ridePayment = paymentService.getPaymentByRideId(createdDemoRide.getRideId());
        if (ridePayment != null) {
            logger.info("Payment for ride: ${}", ridePayment.getAmount());
        }

        // Demonstrates total revenue calculation
        double revenue = paymentService.getTotalRevenue();
        logger.info("Total system revenue: ${}", revenue);

        // Demonstrates getting all payments
        List<Payment> allPayments = paymentService.getAllPayments();
        logger.info("Total payments processed: {}", allPayments.size());
    }

    private void demonstrateAnalytics() throws SQLException {
        logger.info("--- Analytics Service Methods ---");

        // Demonstrates ride statistics
        Map<String, Integer> rideStats = analyticsService.getRidesStatistics();
        logger.info("Ride statistics: {}", rideStats);

        // Demonstrates revenue statistics
        Map<String, Double> revenueStats = analyticsService.getRevenueStatistics();
        logger.info("Revenue statistics: {}", revenueStats);

        // Demonstrates driver performance stats
        Map<String, Integer> driverStats = analyticsService.getDriverPerformanceStats();
        logger.info("Driver performance: {}", driverStats);

        // Demonstrates popular locations
        Map<String, Integer> locations = analyticsService.getPopularLocations();
        logger.info("Popular locations: {}", locations);

        // Demonstrates active users count
        int activeUsers = analyticsService.getActiveUsersCount();
        logger.info("Active users: {}", activeUsers);
    }

    private void demonstrateXmlOperations() {
        logger.info("--- XML Service Methods ---");

        try {
            // Demonstrates SAX parser for promocodes
            List<PromoCode> promoCodes = xmlService.parsePromoCodes("src/main/resources/promocodes.xml");
            logger.info("Parsed {} promocodes using SAX", promoCodes.size());

            // Demonstrates JAXB parser with XSD validation
            List<PromoCode> jaxbPromoCodes = xmlService.parsePromoCodesWithJaxb("src/main/resources/promocodes.xml");
            logger.info("Parsed {} promocodes using JAXB", jaxbPromoCodes.size());

            // Demonstrates SAX parser for support tickets
            List<SupportTicket> supportTickets = xmlService.parseSupportTickets("src/main/resources/supporttickets.xml");
            logger.info("Parsed {} support tickets", supportTickets.size());

            // Show usage examples
            demonstrateParsedDataUsage(promoCodes, supportTickets);

        } catch (Exception e) {
            logger.error("XML operations failed: {}", e.getMessage(), e);
        }
    }

    private void demonstrateParsedDataUsage(List<PromoCode> promoCodes, List<SupportTicket> supportTickets) {
        logger.info("--- Using Parsed XML Data ---");

        // Promo code usage example
        if (!promoCodes.isEmpty()) {
            PromoCode firstPromo = promoCodes.get(0);
            logger.info("First promo code: {} - ${} discount (Valid: {})",
                    firstPromo.getCode(), firstPromo.getDiscount(), firstPromo.isValid());
        }

        // Support ticket statistics
        long openTickets = supportTickets.stream().filter(SupportTicket::isOpen).count();
        long resolvedTickets = supportTickets.stream().filter(SupportTicket::isResolved).count();

        logger.info("Support tickets: {} open, {} resolved", openTickets, resolvedTickets);
    }

    private void demonstrateJsonOperations() {
        logger.info("--- JSON Service Methods ---");

        try {
            // Emergency contacts parsing
            List<EmergencyContact> emergencyContacts =
                    jsonService.parseEmergencyContacts("src/main/resources/emergency_contacts.json");
            logger.info("Parsed {} emergency contacts", emergencyContacts.size());

            // Parsing notifications
            List<Notification> notifications =
                    jsonService.parseNotifications("src/main/resources/notifications.json");
            logger.info("Parsed {} notifications", notifications.size());

            // Using the data
            demonstrateJsonDataUsage(emergencyContacts, notifications);

        } catch (Exception e) {
            logger.error("JSON operations failed: {}", e.getMessage(), e);
        }
    }

    private void demonstrateJsonDataUsage(List<EmergencyContact> emergencyContacts,
                                          List<Notification> notifications) {
        logger.info("--- Using Parsed JSON Data ---");

        // Emergency contacts usage
        if (!emergencyContacts.isEmpty()) {
            EmergencyContact firstContact = emergencyContacts.get(0);
            logger.info("First emergency contact: {} - {}",
                    firstContact.getName(), firstContact.getPhone());
        }

        // Notifications statistics
        long unreadNotifications = notifications.stream()
                .filter(notification -> !notification.isRead())
                .count();
        logger.info("Notifications: {} total, {} unread",
                notifications.size(), unreadNotifications);
    }

    private static void shutdown() {
        logger.info("Shutting down application...");

        try {
            // Clean up connection pool
            ConnectionPool.shutdown();
            logger.info("Connection pool shut down successfully");

        } catch (Exception e) {
            logger.error("Error during shutdown: {}", e.getMessage(), e);
        }

        logger.info("Application shutdown completed");
    }
}