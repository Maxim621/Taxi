package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.PassengerDao;
import com.solvd.taxi.model.Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerDaoImpl extends BaseDao implements PassengerDao {

    @Override
    public Passenger create(Passenger passenger) throws SQLException {
        String sql = "INSERT INTO Passengers (name, phone, email) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, passenger.getName());
            statement.setString(2, passenger.getPhone());
            statement.setString(3, passenger.getEmail());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating passenger failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                passenger.setPassengerId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating passenger failed, no ID obtained.");
            }

            return passenger;
        } finally {
            closeResources(generatedKeys, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Passenger> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Passengers WHERE passenger_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPassenger(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Passenger> findAll() throws SQLException {
        String sql = "SELECT * FROM Passengers";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Passenger> passengers = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                passengers.add(mapResultSetToPassenger(resultSet));
            }

            return passengers;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Passenger passenger) throws SQLException {
        String sql = "UPDATE Passengers SET name = ?, phone = ?, email = ? WHERE passenger_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, passenger.getName());
            statement.setString(2, passenger.getPhone());
            statement.setString(3, passenger.getEmail());
            statement.setInt(4, passenger.getPassengerId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Passengers WHERE passenger_id = ?";
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
    public Optional<Passenger> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Passengers WHERE email = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPassenger(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Passenger> findByPhone(String phone) throws SQLException {
        String sql = "SELECT * FROM Passengers WHERE phone = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPassenger(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Passenger> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM Passengers WHERE name LIKE ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Passenger> passengers = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                passengers.add(mapResultSetToPassenger(resultSet));
            }

            return passengers;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Passengers";
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

    private Passenger mapResultSetToPassenger(ResultSet resultSet) throws SQLException {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(resultSet.getInt("passenger_id"));
        passenger.setName(resultSet.getString("name"));
        passenger.setPhone(resultSet.getString("phone"));
        passenger.setEmail(resultSet.getString("email"));

        Timestamp timestamp = resultSet.getTimestamp("registration_date");
        if (timestamp != null) {
            passenger.setRegistrationDate(timestamp.toLocalDateTime());
        }

        return passenger;
    }
}