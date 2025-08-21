package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.ReviewDao;
import com.solvd.taxi.model.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl extends BaseDao implements ReviewDao {

    @Override
    public Review create(Review review) throws SQLException {
        String sql = "INSERT INTO RideReviews (ride_id, rating, comment) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, review.getRideId());
            statement.setInt(2, review.getRating());
            setNullableString(statement, 3, review.getComment());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating review failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                review.setReviewId(generatedKeys.getInt(1));
            }

            return review;
        } finally {
            closeResources(generatedKeys, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Review> findById(int id) throws SQLException {
        String sql = "SELECT * FROM RideReviews WHERE review_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToReview(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Review> findAll() throws SQLException {
        String sql = "SELECT * FROM RideReviews";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Review> reviews = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                reviews.add(mapResultSetToReview(resultSet));
            }

            return reviews;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Review review) throws SQLException {
        String sql = "UPDATE RideReviews SET ride_id = ?, rating = ?, comment = ? WHERE review_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, review.getRideId());
            statement.setInt(2, review.getRating());
            setNullableString(statement, 3, review.getComment());
            statement.setInt(4, review.getReviewId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM RideReviews WHERE review_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Review> findByRideId(int rideId) throws SQLException {
        String sql = "SELECT * FROM RideReviews WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Review> reviews = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, rideId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                reviews.add(mapResultSetToReview(resultSet));
            }

            return reviews;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Review> findByRating(int rating) throws SQLException {
        String sql = "SELECT * FROM RideReviews WHERE rating = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Review> reviews = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, rating);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                reviews.add(mapResultSetToReview(resultSet));
            }

            return reviews;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Review> findByDriverId(int driverId) throws SQLException {
        String sql = "SELECT rr.* FROM RideReviews rr " +
                "JOIN Rides r ON rr.ride_id = r.ride_id " +
                "WHERE r.driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Review> reviews = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, driverId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                reviews.add(mapResultSetToReview(resultSet));
            }

            return reviews;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Review> findByPassengerId(int passengerId) throws SQLException {
        String sql = "SELECT rr.* FROM RideReviews rr " +
                "JOIN Rides r ON rr.ride_id = r.ride_id " +
                "WHERE r.passenger_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Review> reviews = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, passengerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                reviews.add(mapResultSetToReview(resultSet));
            }

            return reviews;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public double getAverageRatingByDriver(int driverId) throws SQLException {
        String sql = "SELECT AVG(rr.rating) FROM RideReviews rr " +
                "JOIN Rides r ON rr.ride_id = r.ride_id " +
                "WHERE r.driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, driverId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public double getAverageRatingByPassenger(int passengerId) throws SQLException {
        String sql = "SELECT AVG(rr.rating) FROM RideReviews rr " +
                "JOIN Rides r ON rr.ride_id = r.ride_id " +
                "WHERE r.passenger_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, passengerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public int countByDriver(int driverId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM RideReviews rr " +
                "JOIN Rides r ON rr.ride_id = r.ride_id " +
                "WHERE r.driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, driverId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public int countByPassenger(int passengerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM RideReviews rr " +
                "JOIN Rides r ON rr.ride_id = r.ride_id " +
                "WHERE r.passenger_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, passengerId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM RideReviews";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    private Review mapResultSetToReview(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setReviewId(resultSet.getInt("review_id"));
        review.setRideId(resultSet.getInt("ride_id"));
        review.setRating(resultSet.getInt("rating"));
        review.setComment(resultSet.getString("comment"));
        return review;
    }
}