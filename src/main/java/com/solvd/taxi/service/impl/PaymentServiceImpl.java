package com.solvd.taxi.service.impl;

import com.solvd.taxi.dao.interfaces.PaymentDao;
import com.solvd.taxi.dao.impl.PaymentDaoImpl;
import com.solvd.taxi.model.Payment;
import com.solvd.taxi.service.interfaces.PaymentService;
import com.solvd.taxi.util.ExceptionHandler;
import com.solvd.taxi.util.Validator;

import java.sql.SQLException;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao;

    public PaymentServiceImpl() {
        this.paymentDao = new PaymentDaoImpl();
    }

    @Override
    public Payment processPayment(Payment payment) {
        try {
            if (!Validator.isPositiveNumber(payment.getAmount())) {
                throw new IllegalArgumentException("Payment amount must be positive");
            }

            return paymentDao.create(payment);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "process payment");
            throw new RuntimeException("Failed to process payment: " + e.getMessage());
        }
    }

    @Override
    public Payment getPaymentById(int id) {
        try {
            return paymentDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get payment by id");
            throw new RuntimeException("Failed to get payment: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        try {
            return paymentDao.findAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get all payments");
            throw new RuntimeException("Failed to get payments: " + e.getMessage());
        }
    }

    @Override
    public boolean updatePayment(Payment payment) {
        try {
            return paymentDao.update(payment);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "update payment");
            throw new RuntimeException("Failed to update payment: " + e.getMessage());
        }
    }

    @Override
    public boolean refundPayment(int paymentId) {
        try {
            Payment payment = getPaymentById(paymentId);
            payment.setAmount(-payment.getAmount()); // Negative amount for refund
            return paymentDao.update(payment);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, "refund payment");
            throw new RuntimeException("Failed to process refund: " + e.getMessage());
        }
    }

    @Override
    public Payment getPaymentByRideId(int rideId) {
        try {
            return paymentDao.findByRideId(rideId)
                    .orElseThrow(() -> new RuntimeException("Payment not found for ride: " + rideId));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get payment by ride id");
            throw new RuntimeException("Failed to get payment: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getPaymentsByMethod(String paymentMethod) {
        try {
            return paymentDao.findByPaymentMethod(paymentMethod);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get payments by method");
            throw new RuntimeException("Failed to get payments by method: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getPaymentsByAmountRange(double minAmount, double maxAmount) {
        try {
            return paymentDao.findByAmountRange(minAmount, maxAmount);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get payments by amount range");
            throw new RuntimeException("Failed to get payments by amount range: " + e.getMessage());
        }
    }

    @Override
    public List<Payment> getPaymentsByDateRange(String startDate, String endDate) {
        try {
            return paymentDao.findByDateRange(startDate, endDate);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get payments by date range");
            throw new RuntimeException("Failed to get payments by date range: " + e.getMessage());
        }
    }

    @Override
    public double getTotalRevenue() {
        try {
            return paymentDao.getTotalRevenue();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get total revenue");
            throw new RuntimeException("Failed to get total revenue: " + e.getMessage());
        }
    }

    @Override
    public double getRevenueByDateRange(String startDate, String endDate) {
        try {
            return paymentDao.getTotalRevenueByDateRange(startDate, endDate);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get revenue by date range");
            throw new RuntimeException("Failed to get revenue by date range: " + e.getMessage());
        }
    }

    @Override
    public int getTotalPaymentsCount() {
        try {
            return paymentDao.countAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get payments count");
            throw new RuntimeException("Failed to get payments count: " + e.getMessage());
        }
    }

    @Override
    public boolean validatePayment(int paymentId) {
        try {
            Payment payment = getPaymentById(paymentId);
            return payment.getAmount() > 0;
        } catch (Exception e) {
            ExceptionHandler.handleException(e, "validate payment");
            return false;
        }
    }
}