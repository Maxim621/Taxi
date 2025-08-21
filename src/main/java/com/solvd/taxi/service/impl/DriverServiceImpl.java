package com.solvd.taxi.service.impl;

import com.solvd.taxi.dao.interfaces.DriverDao;
import com.solvd.taxi.dao.impl.DriverDaoImpl;
import com.solvd.taxi.model.Driver;
import com.solvd.taxi.service.interfaces.DriverService;
import com.solvd.taxi.util.Validator;
import com.solvd.taxi.util.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;

public class DriverServiceImpl implements DriverService {
    private final DriverDao driverDao;

    public DriverServiceImpl() {
        this.driverDao = new DriverDaoImpl();
    }

    @Override
    public Driver registerDriver(Driver driver) {
        try {
            if (!Validator.isValidLicense(driver.getLicenseNumber())) {
                throw new IllegalArgumentException("Invalid license number format");
            }

            return driverDao.create(driver);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "driver registration");
            throw new RuntimeException("Failed to register driver: " + e.getMessage());
        }
    }

    @Override
    public Driver getDriverById(int id) {
        try {
            return driverDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Driver not found with id: " + id));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get driver by id");
            throw new RuntimeException("Failed to get driver: " + e.getMessage());
        }
    }

    @Override
    public List<Driver> getAllDrivers() {
        try {
            return driverDao.findAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get all drivers");
            throw new RuntimeException("Failed to get drivers: " + e.getMessage());
        }
    }

    @Override
    public boolean updateDriver(Driver driver) {
        try {
            return driverDao.update(driver);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "update driver");
            throw new RuntimeException("Failed to update driver: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteDriver(int id) {
        try {
            return driverDao.delete(id);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "delete driver");
            throw new RuntimeException("Failed to delete driver: " + e.getMessage());
        }
    }

    @Override
    public Driver findDriverByLicense(String licenseNumber) {
        try {
            return driverDao.findByLicense(licenseNumber)
                    .orElseThrow(() -> new RuntimeException("Driver not found with license: " + licenseNumber));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "find driver by license");
            throw new RuntimeException("Failed to find driver: " + e.getMessage());
        }
    }

    @Override
    public List<Driver> searchDriversByName(String name) {
        try {
            return driverDao.findByName(name);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "search drivers by name");
            throw new RuntimeException("Failed to search drivers: " + e.getMessage());
        }
    }

    @Override
    public List<Driver> getDriversByRating(double minRating) {
        try {
            return driverDao.findByRating(minRating);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get drivers by rating");
            throw new RuntimeException("Failed to get drivers by rating: " + e.getMessage());
        }
    }

    @Override
    public boolean updateDriverRating(int driverId, double newRating) {
        try {
            if (!Validator.isValidRating(newRating)) {
                throw new IllegalArgumentException("Rating must be between 1.0 and 5.0");
            }

            return driverDao.updateRating(driverId, newRating);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "update driver rating");
            throw new RuntimeException("Failed to update driver rating: " + e.getMessage());
        }
    }

    @Override
    public int getTotalDriversCount() {
        try {
            return driverDao.countAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get drivers count");
            throw new RuntimeException("Failed to get drivers count: " + e.getMessage());
        }
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        try {
            // Implementation for getting available drivers (not currently on a ride)
            return driverDao.findAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get available drivers");
            throw new RuntimeException("Failed to get available drivers: " + e.getMessage());
        }
    }

    @Override
    public boolean assignCarToDriver(int driverId, int carId) {
        // Implementation would require CarDao
        throw new UnsupportedOperationException("Not implemented yet");
    }
}