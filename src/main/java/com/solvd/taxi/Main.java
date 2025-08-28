package com.solvd.taxi;

import com.solvd.taxi.model.Passenger;
import com.solvd.taxi.model.Driver;
import com.solvd.taxi.model.Ride;
import com.solvd.taxi.model.Payment;
import com.solvd.taxi.model.PromoCode;
import com.solvd.taxi.model.SupportTicket;
import com.solvd.taxi.service.impl.*;
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

    public Main() {
        this.passengerService = new PassengerServiceImpl();
        this.driverService = new DriverServiceImpl();
        this.rideService = new RideServiceImpl();
        this.paymentService = new PaymentServiceImpl();
        this.analyticsService = new AnalyticsServiceImpl();
        this.xmlService = new XmlServiceImpl();

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

    private void runDemo() throws SQLException {
        logger.debug("Running demonstration of taxi services");

        try {
            // 1. Demonstration of working with passengers
            demonstratePassengerOperations();

            // 2. Demonstration of work with drivers
            demonstrateDriverOperations();

            // 3. Demonstrate Ride Operations
            demonstrateRideOperations();

            // 4. Demonstrate Payment Operations
            demonstratePaymentOperations();

            // 5. Demonstrate Analytics
            demonstrateAnalytics();

            // 6. Demonstration of XML operations
            demonstrateXmlOperations();

        } catch (Exception e) {
            logger.error("Demo execution failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void demonstratePassengerOperations() throws SQLException {
        logger.info("=== Demonstrating Passenger Operations ===");

        try {
            // Search for existing passenger by phone (use the updated phone number)
            Passenger foundPassenger = passengerService.findPassengerByPhone("+380501234567");
            if (foundPassenger != null) {
                logger.info("Found passenger by phone: {}, Email: {}",
                        foundPassenger.getName(), foundPassenger.getEmail());

                // Update passenger phone for demo
                foundPassenger.setPhone("+380509998877");
                boolean updated = passengerService.updatePassenger(foundPassenger);
                logger.info("Passenger phone updated: {}", updated);
            } else {
                logger.info("No passenger found with phone: +380501234567, creating new one...");

                // Create new passenger for demo
                Passenger newPassenger = new Passenger();
                newPassenger.setName("Demo User");
                newPassenger.setEmail("demo@example.com");
                newPassenger.setPhone("+380501234567");

                Passenger created = passengerService.registerPassenger(newPassenger);
                logger.info("Created new passenger: {}", created.getName());
            }

            // Search for passenger by email
            Passenger foundByEmail = passengerService.findPassengerByEmail("ivan@example.com");
            if (foundByEmail != null) {
                logger.info("Found passenger by email: {}, Phone: {}",
                        foundByEmail.getName(), foundByEmail.getPhone());
            }

            // Search for passengers by name pattern
            List<Passenger> foundByName = passengerService.searchPassengersByName("Ivan");
            logger.info("Found {} passengers with name containing 'Ivan'", foundByName.size());
            for (Passenger passenger : foundByName) {
                logger.info(" - {} (Phone: {})", passenger.getName(), passenger.getPhone());
            }

            // Get all passengers
            List<Passenger> allPassengers = passengerService.getAllPassengers();
            logger.info("Total passengers in database: {}", allPassengers.size());

            // Get passenger count
            int passengerCount = passengerService.getTotalPassengersCount();
            logger.info("Total passengers count: {}", passengerCount);

        } catch (Exception e) {
            logger.error("Passenger operations failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void demonstrateDriverOperations() throws SQLException {
        logger.info("=== Demonstrating Driver Operations ===");

        try {
            // Find an existing driver by license
            Driver foundDriver = driverService.findDriverByLicense("UA123456");
            if (foundDriver != null) {
                logger.info("Found driver by license: {}, Rating: {}",
                        foundDriver.getName(), foundDriver.getRating());

                // Update driver rating
                boolean ratingUpdated = driverService.updateDriverRating(foundDriver.getDriverId(), 4.9);
                logger.info("Driver rating updated: {}", ratingUpdated);
            }

            // Get all drivers
            List<Driver> allDrivers = driverService.getAllDrivers();
            logger.info("Total drivers: {}", allDrivers.size());

        } catch (Exception e) {
            logger.error("Driver operations failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void demonstrateRideOperations() throws SQLException {
        logger.info("=== Demonstrating Ride Operations ===");

        try {
            // Create a new ride
            Ride newRide = new Ride();
            newRide.setPassengerId(1); // Assume that a passenger with ID=1 exists
            newRide.setDriverId(1);    // Assume that a driver with ID=1 exists
            newRide.setStartLocationId(1);
            newRide.setEndLocationId(2);
            newRide.setPriceId(1);
            newRide.setDistance(12.5);

            Ride createdRide = rideService.createRide(newRide);
            logger.info("Created ride: ID={}, Distance={}km",
                    createdRide.getRideId(), createdRide.getDistance());

            // Start a ride
            boolean rideStarted = rideService.startRide(createdRide.getRideId());
            logger.info("Ride started: {}", rideStarted);

            // Complete a ride
            boolean rideCompleted = rideService.completeRide(createdRide.getRideId());
            logger.info("Ride completed: {}", rideCompleted);

            // Get a passenger's rides
            List<Ride> passengerRides = rideService.getRidesByPassenger(1);
            logger.info("Passenger has {} rides", passengerRides.size());

            // Calculate the price of a ride
            double price = rideService.calculateRidePrice(createdRide.getRideId());
            logger.info("Ride price: ${}", price);

        } catch (Exception e) {
            logger.error("Ride operations failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void demonstratePaymentOperations() throws SQLException {
        logger.info("=== Demonstrating Payment Operations ===");

        try {
            int newRideId = 11;

            // Create a payment
            Payment newPayment = new Payment();
            newPayment.setRideId(newRideId); // Assume that the trip with ID=1 exists
            newPayment.setAmount(25.75);
            newPayment.setPaymentMethod("card");

            Payment createdPayment = paymentService.processPayment(newPayment);
            logger.info("Processed payment: ID={}, Amount=${}",
                    createdPayment.getPaymentId(), createdPayment.getAmount());

            // Get payment for a ride
            Payment ridePayment = paymentService.getPaymentByRideId(1);
            logger.info("Payment for ride: ${}", ridePayment.getAmount());

            // Get total revenue
            double totalRevenue = paymentService.getTotalRevenue();
            logger.info("Total revenue: ${}", totalRevenue);

            // Get all payments
            List<Payment> allPayments = paymentService.getAllPayments();
            logger.info("Total payments: {}", allPayments.size());

        } catch (Exception e) {
            logger.error("Payment operations failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void demonstrateAnalytics() throws SQLException {
        logger.info("=== Demonstrating Analytics Operations ===");

        try {
            // Ride Statistics
            Map<String, Integer> rideStats = analyticsService.getRidesStatistics();
            logger.info("Ride statistics: {}", rideStats);

            // Revenue Statistics
            Map<String, Double> revenueStats = analyticsService.getRevenueStatistics();
            logger.info("Revenue statistics: {}", revenueStats);

            // Driver Statistics
            Map<String, Integer> driverStats = analyticsService.getDriverPerformanceStats();
            logger.info("Driver statistics: {}", driverStats);

            // Popular Locations
            Map<String, Integer> popularLocations = analyticsService.getPopularLocations();
            logger.info("Popular locations: {}", popularLocations);

            // Active Users
            int activeUsers = analyticsService.getActiveUsersCount();
            logger.info("Active users: {}", activeUsers);

        } catch (Exception e) {
            logger.error("Analytics operations failed: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void demonstrateXmlOperations() {
        logger.info("=== Demonstrating XML Operations with SAX Parser ===");

        try {
            // Parse promocodes from XML using SAX parser
            List<PromoCode> promoCodes = xmlService.parsePromoCodes("src/main/resources/promocodes.xml");
            logger.info("Parsed {} promocodes from XML using SAX parser", promoCodes.size());

            // JAXB parser
            List<PromoCode> promoCodesJaxb = xmlService.parsePromoCodesWithJaxb("src/main/resources/promocodes.xml");
            logger.info("Parsed {} promocodes from XML using JAXB with XSD validation", promoCodesJaxb.size());

            // Parse support tickets from XML using SAX parser
            List<SupportTicket> supportTickets = xmlService.parseSupportTickets("src/main/resources/supporttickets.xml");
            logger.info("Parsed {} support tickets from XML using SAX parser", supportTickets.size());

            // Demonstrate usage of parsed data
            demonstratePromoCodeUsage(promoCodes);
            demonstrateSupportTicketUsage(supportTickets);

        } catch (Exception e) {
            logger.error("XML operations failed: {}", e.getMessage(), e);
        }
    }

    private void demonstratePromoCodeUsage(List<PromoCode> promoCodes) {
        logger.info("=== Using PromoCodes parsed with SAX ===");
        for (PromoCode promoCode : promoCodes) {
            logger.info("PromoCode: {} - ${} discount, expires: {}, valid: {}",
                    promoCode.getCode(), promoCode.getDiscount(),
                    promoCode.getExpiryDate(), promoCode.isValid());
        }
    }

    private void demonstrateSupportTicketUsage(List<SupportTicket> supportTickets) {
        logger.info("=== Using SupportTickets parsed with SAX ===");
        for (SupportTicket ticket : supportTickets) {
            String status = ticket.isResolved() ? "RESOLVED" : "OPEN";
            logger.info("Ticket #{}: {} - {} (Created: {})",
                    ticket.getTicketId(), ticket.getSubject(), status, ticket.getCreatedAt());
        }

        // Show statistics
        long openTickets = supportTickets.stream().filter(SupportTicket::isOpen).count();
        long resolvedTickets = supportTickets.stream().filter(SupportTicket::isResolved).count();

        logger.info("Support tickets statistics: {} open, {} resolved", openTickets, resolvedTickets);
    }


    private static void shutdown() {
        logger.info("Shutting down application...");

        try {
            // Close the ConnectionPool
            ConnectionPool.shutdown();
            logger.info("Connection pool shut down successfully");

        } catch (Exception e) {
            logger.error("Error during shutdown: {}", e.getMessage(), e);
        }

        logger.info("Application shutdown completed");
    }
}