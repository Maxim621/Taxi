package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.RideDao;
import com.solvd.taxi.model.Ride;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RideDaoImpl extends BaseDao implements RideDao {

    @Override
    public Ride create(Ride ride) throws SQLException {
        String sql = "INSERT INTO Rides (passenger_id, driver_id, start_location_id, " +
                "end_location_id, price_id, start_time, end_time, status, distance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, ride.getPassengerId());
            statement.setInt(2, ride.getDriverId());
            statement.setInt(3, ride.getStartLocationId());
            statement.setInt(4, ride.getEndLocationId());
            statement.setInt(5, ride.getPriceId());
            setNullableTimestamp(statement, 6, ride.getStartTime() != null ?
                    Timestamp.valueOf(ride.getStartTime()) : null);
            setNullableTimestamp(statement, 7, ride.getEndTime() != null ?
                    Timestamp.valueOf(ride.getEndTime()) : null);
            statement.setString(8, ride.getStatus());
            statement.setDouble(9, ride.getDistance());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating ride failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                ride.setRideId(generatedKeys.getInt(1));
            }

            return ride;
        } finally {
            closeResources(generatedKeys, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Ride> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Rides WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToRide(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Ride> findAll() throws SQLException {
        String sql = "SELECT * FROM Rides";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Ride> rides = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                rides.add(mapResultSetToRide(resultSet));
            }

            return rides;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Ride ride) throws SQLException {
        String sql = "UPDATE Rides SET passenger_id = ?, driver_id = ?, start_location_id = ?, " +
                "end_location_id = ?, price_id = ?, start_time = ?, end_time = ?, " +
                "status = ?, distance = ? WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, ride.getPassengerId());
            statement.setInt(2, ride.getDriverId());
            statement.setInt(3, ride.getStartLocationId());
            statement.setInt(4, ride.getEndLocationId());
            statement.setInt(5, ride.getPriceId());
            setNullableTimestamp(statement, 6, ride.getStartTime() != null ?
                    Timestamp.valueOf(ride.getStartTime()) : null);
            setNullableTimestamp(statement, 7, ride.getEndTime() != null ?
                    Timestamp.valueOf(ride.getEndTime()) : null);
            statement.setString(8, ride.getStatus());
            statement.setDouble(9, ride.getDistance());
            statement.setInt(10, ride.getRideId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Rides WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Ride> findByPassengerId(int passengerId) throws SQLException {
        String sql = "SELECT * FROM Rides WHERE passenger_id = ? ORDER BY start_time DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ride> rides = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, passengerId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                rides.add(mapResultSetToRide(resultSet));
            }

            return rides;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Ride> findByDriverId(int driverId) throws SQLException {
        String sql = "SELECT * FROM Rides WHERE driver_id = ? ORDER BY start_time DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ride> rides = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, driverId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                rides.add(mapResultSetToRide(resultSet));
            }

            return rides;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Ride> findByStatus(String status) throws SQLException {
        String sql = "SELECT * FROM Rides WHERE status = ? ORDER BY start_time DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ride> rides = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                rides.add(mapResultSetToRide(resultSet));
            }

            return rides;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Ride> findByDateRange(String startDate, String endDate) throws SQLException {
        String sql = "SELECT * FROM Rides WHERE DATE(start_time) BETWEEN ? AND ? ORDER BY start_time DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Ride> rides = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                rides.add(mapResultSetToRide(resultSet));
            }

            return rides;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean updateStatus(int rideId, String status) throws SQLException {
        String sql = "UPDATE Rides SET status = ? WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, status);
            statement.setInt(2, rideId);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean completeRide(int rideId, String endTime) throws SQLException {
        String sql = "UPDATE Rides SET status = 'completed', end_time = ? WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, endTime);
            statement.setInt(2, rideId);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Ride> findActiveRides() throws SQLException {
        String sql = "SELECT * FROM Rides WHERE status IN ('pending', 'ongoing') ORDER BY start_time DESC";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Ride> rides = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                rides.add(mapResultSetToRide(resultSet));
            }

            return rides;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public double getTotalDistanceByPassenger(int passengerId) throws SQLException {
        String sql = "SELECT SUM(distance) FROM Rides WHERE passenger_id = ? AND status = 'completed'";
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
            closeResources(resultSet, statement, connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Rides";
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
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public int countByStatus(String status) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Rides WHERE status = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, status);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    private Ride mapResultSetToRide(ResultSet resultSet) throws SQLException {
        Ride ride = new Ride();
        ride.setRideId(resultSet.getInt("ride_id"));
        ride.setPassengerId(resultSet.getInt("passenger_id"));
        ride.setDriverId(resultSet.getInt("driver_id"));
        ride.setStartLocationId(resultSet.getInt("start_location_id"));
        ride.setEndLocationId(resultSet.getInt("end_location_id"));
        ride.setPriceId(resultSet.getInt("price_id"));

        Timestamp startTime = resultSet.getTimestamp("start_time");
        if (startTime != null) {
            ride.setStartTime(startTime.toLocalDateTime());
        }

        Timestamp endTime = resultSet.getTimestamp("end_time");
        if (endTime != null) {
            ride.setEndTime(endTime.toLocalDateTime());
        }

        ride.setStatus(resultSet.getString("status"));
        ride.setDistance(resultSet.getDouble("distance"));

        return ride;
    }
}