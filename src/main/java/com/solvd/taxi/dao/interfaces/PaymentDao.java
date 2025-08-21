package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Payment;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PaymentDao {
    Payment create(Payment payment) throws SQLException;
    Optional<Payment> findById(int id) throws SQLException;
    List<Payment> findAll() throws SQLException;
    boolean update(Payment payment) throws SQLException;
    boolean delete(int id) throws SQLException;
    Optional<Payment> findByRideId(int rideId) throws SQLException;
    List<Payment> findByPaymentMethod(String paymentMethod) throws SQLException;
    List<Payment> findByAmountRange(double minAmount, double maxAmount) throws SQLException;
    List<Payment> findByDateRange(String startDate, String endDate) throws SQLException;
    double getTotalRevenue() throws SQLException;
    double getTotalRevenueByDateRange(String startDate, String endDate) throws SQLException;
    int countAll() throws SQLException;
}