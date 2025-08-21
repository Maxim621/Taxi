package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.PaymentDao;
import com.solvd.taxi.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentDaoImpl extends BaseDao implements PaymentDao {

    @Override
    public Payment create(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payments (ride_id, amount, payment_method) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, payment.getRideId());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPaymentMethod());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                payment.setPaymentId(generatedKeys.getInt(1));
            }

            return payment;
        } finally {
            closeResources(generatedKeys, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Payment> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE payment_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPayment(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Payment> findAll() throws SQLException {
        String sql = "SELECT * FROM Payments";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }

            return payments;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE Payments SET ride_id = ?, amount = ?, payment_method = ? WHERE payment_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, payment.getRideId());
            statement.setDouble(2, payment.getAmount());
            statement.setString(3, payment.getPaymentMethod());
            statement.setInt(4, payment.getPaymentId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Payments WHERE payment_id = ?";
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
    public Optional<Payment> findByRideId(int rideId) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE ride_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, rideId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPayment(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Payment> findByPaymentMethod(String paymentMethod) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE payment_method = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, paymentMethod);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }

            return payments;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Payment> findByAmountRange(double minAmount, double maxAmount) throws SQLException {
        String sql = "SELECT * FROM Payments WHERE amount BETWEEN ? AND ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, minAmount);
            statement.setDouble(2, maxAmount);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }

            return payments;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Payment> findByDateRange(String startDate, String endDate) throws SQLException {
        String sql = "SELECT p.* FROM Payments p " +
                "JOIN Rides r ON p.ride_id = r.ride_id " +
                "WHERE DATE(r.start_time) BETWEEN ? AND ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, startDate);
            statement.setString(2, endDate);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }

            return payments;
        } finally {
            closeResources(resultSet, statement);
            releaseConnection(connection);
        }
    }

    @Override
    public double getTotalRevenue() throws SQLException {
        String sql = "SELECT SUM(amount) FROM Payments";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

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
    public double getTotalRevenueByDateRange(String startDate, String endDate) throws SQLException {
        String sql = "SELECT SUM(p.amount) FROM Payments p " +
                "JOIN Rides r ON p.ride_id = r.ride_id " +
                "WHERE DATE(r.start_time) BETWEEN ? AND ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, startDate);
            statement.setString(2, endDate);
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
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Payments";
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

    private Payment mapResultSetToPayment(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setPaymentId(resultSet.getInt("payment_id"));
        payment.setRideId(resultSet.getInt("ride_id"));
        payment.setAmount(resultSet.getDouble("amount"));
        payment.setPaymentMethod(resultSet.getString("payment_method"));
        return payment;
    }
}