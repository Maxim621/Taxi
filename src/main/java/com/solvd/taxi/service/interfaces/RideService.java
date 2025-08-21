package com.solvd.taxi.service.interfaces;

import com.solvd.taxi.model.Ride;

import java.sql.SQLException;
import java.util.List;

public interface RideService {
    Ride createRide(Ride ride) throws SQLException;
    Ride getRideById(int id) throws SQLException;
    List<Ride> getAllRides() throws SQLException;
    boolean updateRide(Ride ride) throws SQLException;
    boolean cancelRide(int rideId) throws SQLException;
    List<Ride> getRidesByPassenger(int passengerId) throws SQLException;
    List<Ride> getRidesByDriver(int driverId) throws SQLException;
    List<Ride> getRidesByStatus(String status) throws SQLException;
    boolean startRide(int rideId) throws SQLException;
    boolean completeRide(int rideId) throws SQLException;
    List<Ride> getActiveRides() throws SQLException;
    double calculateRidePrice(int rideId) throws SQLException;
    int getTotalRidesCount() throws SQLException;
    int getRidesCountByStatus(String status) throws SQLException;
    List<Ride> getRidesByDateRange(String startDate, String endDate) throws SQLException;
}