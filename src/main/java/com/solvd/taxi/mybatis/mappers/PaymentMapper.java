package com.solvd.taxi.mybatis.mappers;

import com.solvd.taxi.model.Payment;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PaymentMapper {
    Payment findById(int id);
    Payment findByRideId(int rideId);
    List<Payment> findByPaymentMethod(String paymentMethod);
    List<Payment> findByAmountRange(@Param("minAmount") double minAmount, @Param("maxAmount") double maxAmount);
    List<Payment> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    List<Payment> findAll();
    int getTotalCount();
    double getTotalRevenue();
    double getRevenueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    void create(Payment payment);
    void update(Payment payment);
    void refund(int paymentId);
    void validate(int paymentId);
    void delete(int id);
}