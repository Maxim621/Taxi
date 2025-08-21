package com.solvd.taxi.service.impl;

import com.solvd.taxi.dao.interfaces.RideDao;
import com.solvd.taxi.dao.impl.RideDaoImpl;
import com.solvd.taxi.model.Ride;
import com.solvd.taxi.service.interfaces.RideService;
import com.solvd.taxi.util.ExceptionHandler;
import com.solvd.taxi.util.DateUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class RideServiceImpl implements RideService {
    private final RideDao rideDao;

    public RideServiceImpl() {
        this.rideDao = new RideDaoImpl();
    }

    @Override
    public Ride createRide(Ride ride) {
        try {
            ride.setStartTime(LocalDateTime.now());
            ride.setStatus("pending");
            return rideDao.create(ride);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "create ride");
            throw new RuntimeException("Failed to create ride: " + e.getMessage());
        }
    }

    @Override
    public Ride getRideById(int id) {
        try {
            return rideDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Ride not found with id: " + id));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get ride by id");
            throw new RuntimeException("Failed to get ride: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getAllRides() {
        try {
            return rideDao.findAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get all rides");
            throw new RuntimeException("Failed to get rides: " + e.getMessage());
        }
    }

    @Override
    public boolean updateRide(Ride ride) {
        try {
            return rideDao.update(ride);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "update ride");
            throw new RuntimeException("Failed to update ride: " + e.getMessage());
        }
    }

    @Override
    public boolean cancelRide(int rideId) {
        try {
            return rideDao.updateStatus(rideId, "cancelled");
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "cancel ride");
            throw new RuntimeException("Failed to cancel ride: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getRidesByPassenger(int passengerId) {
        try {
            return rideDao.findByPassengerId(passengerId);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get rides by passenger");
            throw new RuntimeException("Failed to get passenger rides: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getRidesByDriver(int driverId) {
        try {
            return rideDao.findByDriverId(driverId);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get rides by driver");
            throw new RuntimeException("Failed to get driver rides: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getRidesByStatus(String status) {
        try {
            return rideDao.findByStatus(status);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get rides by status");
            throw new RuntimeException("Failed to get rides by status: " + e.getMessage());
        }
    }

    @Override
    public boolean startRide(int rideId) {
        try {
            return rideDao.updateStatus(rideId, "ongoing");
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "start ride");
            throw new RuntimeException("Failed to start ride: " + e.getMessage());
        }
    }

    @Override
    public boolean completeRide(int rideId) {
        try {
            String endTime = DateUtils.formatForDatabase(LocalDateTime.now());
            return rideDao.completeRide(rideId, endTime);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "complete ride");
            throw new RuntimeException("Failed to complete ride: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getActiveRides() {
        try {
            return rideDao.findActiveRides();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get active rides");
            throw new RuntimeException("Failed to get active rides: " + e.getMessage());
        }
    }

    @Override
    public double calculateRidePrice(int rideId) {
        // Implementation would require PricingService
        Ride ride = getRideById(rideId);
        // Simple calculation for demonstration
        return 5.0 + (ride.getDistance() * 1.5);
    }

    @Override
    public int getTotalRidesCount() {
        try {
            return rideDao.countAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get rides count");
            throw new RuntimeException("Failed to get rides count: " + e.getMessage());
        }
    }

    @Override
    public int getRidesCountByStatus(String status) {
        try {
            return rideDao.countByStatus(status);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get rides count by status");
            throw new RuntimeException("Failed to get rides count by status: " + e.getMessage());
        }
    }

    @Override
    public List<Ride> getRidesByDateRange(String startDate, String endDate) {
        try {
            return rideDao.findByDateRange(startDate, endDate);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get rides by date range");
            throw new RuntimeException("Failed to get rides by date range: " + e.getMessage());
        }
    }
}