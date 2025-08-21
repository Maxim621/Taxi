package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Ride;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RideDao {
    Ride create(Ride ride) throws SQLException;
    Optional<Ride> findById(int id) throws SQLException;
    List<Ride> findAll() throws SQLException;
    boolean update(Ride ride) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<Ride> findByPassengerId(int passengerId) throws SQLException;
    List<Ride> findByDriverId(int driverId) throws SQLException;
    List<Ride> findByStatus(String status) throws SQLException;
    List<Ride> findByDateRange(String startDate, String endDate) throws SQLException;
    boolean updateStatus(int rideId, String status) throws SQLException;
    boolean completeRide(int rideId, String endTime) throws SQLException;
    List<Ride> findActiveRides() throws SQLException;
    double getTotalDistanceByPassenger(int passengerId) throws SQLException;
    int countAll() throws SQLException;
    int countByStatus(String status) throws SQLException;
}