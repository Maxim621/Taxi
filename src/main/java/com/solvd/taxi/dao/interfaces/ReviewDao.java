package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Review;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    Review create(Review review) throws SQLException;
    Optional<Review> findById(int id) throws SQLException;
    List<Review> findAll() throws SQLException;
    boolean update(Review review) throws SQLException;
    boolean delete(int id) throws SQLException;
    List<Review> findByRideId(int rideId) throws SQLException;
    List<Review> findByRating(int rating) throws SQLException;
    List<Review> findByDriverId(int driverId) throws SQLException;
    List<Review> findByPassengerId(int passengerId) throws SQLException;
    double getAverageRatingByDriver(int driverId) throws SQLException;
    double getAverageRatingByPassenger(int passengerId) throws SQLException;
    int countByDriver(int driverId) throws SQLException;
    int countByPassenger(int passengerId) throws SQLException;
    int countAll() throws SQLException;
}