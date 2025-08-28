package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.DriverDao;
import com.solvd.taxi.model.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverDaoImpl extends BaseDao implements DriverDao {

    @Override
    public Driver create(Driver driver) throws SQLException {
        String sql = "INSERT INTO Drivers (name, license_number, rating) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setDouble(3, driver.getRating());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating driver failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                driver.setDriverId(generatedKeys.getInt(1));
            }

            return driver;
        } finally {
            closeResources(generatedKeys, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Driver> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Drivers WHERE driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToDriver(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Driver> findAll() throws SQLException {
        String sql = "SELECT * FROM Drivers";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Driver> drivers = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                drivers.add(mapResultSetToDriver(resultSet));
            }

            return drivers;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Driver driver) throws SQLException {
        String sql = "UPDATE Drivers SET name = ?, license_number = ?, rating = ? WHERE driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, driver.getName());
            statement.setString(2, driver.getLicenseNumber());
            statement.setDouble(3, driver.getRating());
            statement.setInt(4, driver.getDriverId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Drivers WHERE driver_id = ?";
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
    public Optional<Driver> findByLicense(String licenseNumber) throws SQLException {
        String sql = "SELECT * FROM Drivers WHERE license_number = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, licenseNumber);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToDriver(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Driver> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM Drivers WHERE name LIKE ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Driver> drivers = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                drivers.add(mapResultSetToDriver(resultSet));
            }

            return drivers;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Driver> findByRating(double minRating) throws SQLException {
        String sql = "SELECT * FROM Drivers WHERE rating >= ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Driver> drivers = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, minRating);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                drivers.add(mapResultSetToDriver(resultSet));
            }

            return drivers;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean updateRating(int driverId, double newRating) throws SQLException {
        String sql = "UPDATE Drivers SET rating = ? WHERE driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setDouble(1, newRating);
            statement.setInt(2, driverId);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Drivers";
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

    private Driver mapResultSetToDriver(ResultSet resultSet) throws SQLException {
        Driver driver = new Driver();
        driver.setDriverId(resultSet.getInt("driver_id"));
        driver.setName(resultSet.getString("name"));
        driver.setLicenseNumber(resultSet.getString("license_number"));
        driver.setRating(resultSet.getDouble("rating"));
        return driver;
    }
}