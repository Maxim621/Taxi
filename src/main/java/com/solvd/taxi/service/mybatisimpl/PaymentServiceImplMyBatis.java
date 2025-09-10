package com.solvd.taxi.service.mybatisimpl;

import com.solvd.taxi.model.Payment;
import com.solvd.taxi.mybatis.mappers.PaymentMapper;
import com.solvd.taxi.service.interfaces.PaymentService;
import com.solvd.taxi.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImplMyBatis implements PaymentService {
    private static final Logger logger = LogManager.getLogger(PaymentServiceImplMyBatis.class);

    @Override
    public Payment processPayment(Payment payment) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            mapper.create(payment);
            session.commit();
            logger.info("Payment processed with ID: {}", payment.getPaymentId());
            return payment;
        }
    }

    @Override
    public Payment getPaymentById(int id) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.findById(id);
        }
    }

    @Override
    public List<Payment> getAllPayments() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public boolean updatePayment(Payment payment) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            mapper.update(payment);
            session.commit();
            return true;
        }
    }

    @Override
    public boolean refundPayment(int paymentId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            mapper.refund(paymentId);
            session.commit();
            return true;
        }
    }

    @Override
    public Payment getPaymentByRideId(int rideId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.findByRideId(rideId);
        }
    }

    @Override
    public List<Payment> getPaymentsByMethod(String paymentMethod) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.findByPaymentMethod(paymentMethod);
        }
    }

    @Override
    public List<Payment> getPaymentsByAmountRange(double minAmount, double maxAmount) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.findByAmountRange(minAmount, maxAmount);
        }
    }

    @Override
    public List<Payment> getPaymentsByDateRange(String startDate, String endDate) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            return mapper.findByDateRange(start, end);
        }
    }

    @Override
    public double getTotalRevenue() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.getTotalRevenue();
        }
    }

    @Override
    public double getRevenueByDateRange(String startDate, String endDate) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            return mapper.getRevenueByDateRange(start, end);
        }
    }

    @Override
    public int getTotalPaymentsCount() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            return mapper.getTotalCount();
        }
    }

    @Override
    public boolean validatePayment(int paymentId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PaymentMapper mapper = session.getMapper(PaymentMapper.class);
            mapper.validate(paymentId);
            session.commit();
            return true;
        }
    }
}