package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.LocationDao;
import com.solvd.taxi.model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LocationDaoImpl extends BaseDao implements LocationDao {

    @Override
    public Location create(Location location) throws SQLException {
        String sql = "INSERT INTO Locations (address, latitude, longitude) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, location.getAddress());
            setNullableDouble(statement, 2, location.getLatitude());
            setNullableDouble(statement, 3, location.getLongitude());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating location failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                location.setLocationId(generatedKeys.getInt(1));
            }

            return location;
        } finally {
            closeResources(generatedKeys, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Location> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Locations WHERE location_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToLocation(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Location> findAll() throws SQLException {
        String sql = "SELECT * FROM Locations";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Location> locations = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                locations.add(mapResultSetToLocation(resultSet));
            }

            return locations;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Location location) throws SQLException {
        String sql = "UPDATE Locations SET address = ?, latitude = ?, longitude = ? WHERE location_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, location.getAddress());
            setNullableDouble(statement, 2, location.getLatitude());
            setNullableDouble(statement, 3, location.getLongitude());
            statement.setInt(4, location.getLocationId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Locations WHERE location_id = ?";
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
    public Optional<Location> findByAddress(String address) throws SQLException {
        String sql = "SELECT * FROM Locations WHERE address = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, address);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToLocation(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Location> findByCoordinates(double minLat, double maxLat, double minLon, double maxLon) throws SQLException {
        String sql = "SELECT * FROM Locations WHERE latitude BETWEEN ? AND ? AND longitude BETWEEN ? AND ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Location> locations = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, minLat);
            statement.setDouble(2, maxLat);
            statement.setDouble(3, minLon);
            statement.setDouble(4, maxLon);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                locations.add(mapResultSetToLocation(resultSet));
            }

            return locations;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Location> findByNamePattern(String namePattern) throws SQLException {
        String sql = "SELECT * FROM Locations WHERE address LIKE ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Location> locations = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + namePattern + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                locations.add(mapResultSetToLocation(resultSet));
            }

            return locations;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Location> findByExactCoordinates(double latitude, double longitude) throws SQLException {
        String sql = "SELECT * FROM Locations WHERE latitude = ? AND longitude = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToLocation(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Locations";
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

    private Location mapResultSetToLocation(ResultSet resultSet) throws SQLException {
        Location location = new Location();
        location.setLocationId(resultSet.getInt("location_id"));
        location.setAddress(resultSet.getString("address"));
        location.setLatitude(getDouble(resultSet, "latitude"));
        location.setLongitude(getDouble(resultSet, "longitude"));
        return location;
    }

    private Double getDouble(ResultSet resultSet, String columnName) throws SQLException {
        double value = resultSet.getDouble(columnName);
        return resultSet.wasNull() ? null : value;
    }
}