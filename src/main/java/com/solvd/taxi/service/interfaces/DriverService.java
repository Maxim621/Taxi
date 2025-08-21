package com.solvd.taxi.service.interfaces;

import com.solvd.taxi.model.Driver;

import java.sql.SQLException;
import java.util.List;

public interface DriverService {
    Driver registerDriver(Driver driver) throws SQLException;
    Driver getDriverById(int id) throws SQLException;
    List<Driver> getAllDrivers() throws SQLException;
    boolean updateDriver(Driver driver) throws SQLException;
    boolean deleteDriver(int id) throws SQLException;
    Driver findDriverByLicense(String licenseNumber) throws SQLException;
    List<Driver> searchDriversByName(String name) throws SQLException;
    List<Driver> getDriversByRating(double minRating) throws SQLException;
    boolean updateDriverRating(int driverId, double newRating) throws SQLException;
    int getTotalDriversCount() throws SQLException;
    List<Driver> getAvailableDrivers() throws SQLException;
    boolean assignCarToDriver(int driverId, int carId) throws SQLException;
}