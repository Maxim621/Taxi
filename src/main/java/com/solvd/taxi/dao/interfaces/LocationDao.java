package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Location create(Location location) throws SQLException;
    Optional<Location> findById(int id) throws SQLException;
    List<Location> findAll() throws SQLException;
    boolean update(Location location) throws SQLException;
    boolean delete(int id) throws SQLException;
    Optional<Location> findByAddress(String address) throws SQLException;
    List<Location> findByCoordinates(double minLat, double maxLat, double minLon, double maxLon) throws SQLException;
    List<Location> findByNamePattern(String namePattern) throws SQLException;
    Optional<Location> findByExactCoordinates(double latitude, double longitude) throws SQLException;
    int countAll() throws SQLException;
}