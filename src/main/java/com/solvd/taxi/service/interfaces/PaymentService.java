package com.solvd.taxi.service.interfaces;

import com.solvd.taxi.model.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentService {
    Payment processPayment(Payment payment) throws SQLException;
    Payment getPaymentById(int id) throws SQLException;
    List<Payment> getAllPayments() throws SQLException;
    boolean updatePayment(Payment payment) throws SQLException;
    boolean refundPayment(int paymentId) throws SQLException;
    Payment getPaymentByRideId(int rideId) throws SQLException;
    List<Payment> getPaymentsByMethod(String paymentMethod) throws SQLException;
    List<Payment> getPaymentsByAmountRange(double minAmount, double maxAmount) throws SQLException;
    List<Payment> getPaymentsByDateRange(String startDate, String endDate) throws SQLException;
    double getTotalRevenue();
    double getRevenueByDateRange(String startDate, String endDate) throws SQLException;
    int getTotalPaymentsCount() throws SQLException;
    boolean validatePayment(int paymentId) throws SQLException;
}