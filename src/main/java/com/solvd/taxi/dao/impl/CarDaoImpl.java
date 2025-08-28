package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.CarDao;
import com.solvd.taxi.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl extends BaseDao implements CarDao {

    @Override
    public Car create(Car car) throws SQLException {
        String sql = "INSERT INTO Cars (driver_id, model, plate_number, type_id) VALUES (?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            setNullableInt(statement, 1, car.getDriverId());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getPlateNumber());
            setNullableInt(statement, 4, car.getTypeId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setCarId(generatedKeys.getInt(1));
            }

            return car;
        } finally {
            closeResources(generatedKeys, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Car> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Cars WHERE car_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToCar(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Car> findAll() throws SQLException {
        String sql = "SELECT * FROM Cars";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Car> cars = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                cars.add(mapResultSetToCar(resultSet));
            }

            return cars;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Car car) throws SQLException {
        String sql = "UPDATE Cars SET driver_id = ?, model = ?, plate_number = ?, type_id = ? WHERE car_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            setNullableInt(statement, 1, car.getDriverId());
            statement.setString(2, car.getModel());
            statement.setString(3, car.getPlateNumber());
            setNullableInt(statement, 4, car.getTypeId());
            statement.setInt(5, car.getCarId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Cars WHERE car_id = ?";
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
    public Optional<Car> findByDriverId(int driverId) throws SQLException {
        String sql = "SELECT * FROM Cars WHERE driver_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, driverId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToCar(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Car> findByPlateNumber(String plateNumber) throws SQLException {
        String sql = "SELECT * FROM Cars WHERE plate_number = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, plateNumber);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToCar(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Car> findByModel(String model) throws SQLException {
        String sql = "SELECT * FROM Cars WHERE model LIKE ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Car> cars = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + model + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cars.add(mapResultSetToCar(resultSet));
            }

            return cars;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Car> findByType(int typeId) throws SQLException {
        String sql = "SELECT * FROM Cars WHERE type_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Car> cars = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, typeId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                cars.add(mapResultSetToCar(resultSet));
            }

            return cars;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean updateDriverAssignment(int carId, Integer newDriverId) throws SQLException {
        String sql = "UPDATE Cars SET driver_id = ? WHERE car_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            setNullableInt(statement, 1, newDriverId);
            statement.setInt(2, carId);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Cars";
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

    private Car mapResultSetToCar(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setCarId(resultSet.getInt("car_id"));
        car.setDriverId(getInteger(resultSet, "driver_id"));
        car.setModel(resultSet.getString("model"));
        car.setPlateNumber(resultSet.getString("plate_number"));
        car.setTypeId(getInteger(resultSet, "type_id"));
        return car;
    }

    private Integer getInteger(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        return resultSet.wasNull() ? null : value;
    }
}