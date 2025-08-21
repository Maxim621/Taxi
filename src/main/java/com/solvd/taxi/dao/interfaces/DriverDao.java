package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Driver;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DriverDao {
    Driver create(Driver driver) throws SQLException;
    Optional<Driver> findById(int id) throws SQLException;
    List<Driver> findAll() throws SQLException;
    boolean update(Driver driver) throws SQLException;
    boolean delete(int id) throws SQLException;
    Optional<Driver> findByLicense(String licenseNumber) throws SQLException;
    List<Driver> findByName(String name) throws SQLException;
    List<Driver> findByRating(double minRating) throws SQLException;
    boolean updateRating(int driverId, double newRating) throws SQLException;
    int countAll() throws SQLException;
}