package com.solvd.taxi.service.mybatisimpl;

import com.solvd.taxi.mybatis.mappers.AnalyticsMapper;
import com.solvd.taxi.service.interfaces.AnalyticsService;
import com.solvd.taxi.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsServiceImplMyBatis implements AnalyticsService {
    private static final Logger logger = LogManager.getLogger(AnalyticsServiceImplMyBatis.class);

    @Override
    public Map<String, Integer> getRidesStatistics() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getRidesStatistics();
        }
    }

    @Override
    public Map<String, Double> getRevenueStatistics() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getRevenueStatistics();
        }
    }

    @Override
    public Map<String, Integer> getDriverPerformanceStats() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            List<Map<String, Object>> results = mapper.getDriverPerformanceStats();

            Map<String, Integer> stats = new HashMap<>();
            stats.put("total_drivers", results.size());

            double avgRating = results.stream()
                    .mapToDouble(result -> ((Number) result.get("avg_rating")).doubleValue())
                    .average()
                    .orElse(0.0);
            stats.put("avg_rating", (int) Math.round(avgRating));

            return stats;
        }
    }

    @Override
    public Map<String, Integer> getPassengerActivityStats() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getPassengerActivityStats();
        }
    }

    @Override
    public Map<String, Integer> getPopularLocations() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getPopularLocations();
        }
    }

    @Override
    public Map<String, Integer> getRidesByHourOfDay() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getRidesByHourOfDay();
        }
    }

    @Override
    public Map<String, Integer> getRidesByDayOfWeek() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getRidesByDayOfWeek();
        }
    }

    @Override
    public double getAverageRideDistance() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getAverageRideDistance();
        }
    }

    @Override
    public double getAverageRideDuration() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getAverageRideDuration();
        }
    }

    @Override
    public Map<String, Integer> getPaymentMethodsDistribution() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getPaymentMethodsDistribution();
        }
    }

    @Override
    public Map<Integer, Integer> getRatingDistribution() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getRatingDistribution();
        }
    }

    @Override
    public Map<String, Integer> getCarTypeUsageStats() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getCarTypeUsageStats();
        }
    }

    @Override
    public Map<String, Double> getRevenueByLocation() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getRevenueByLocation();
        }
    }

    @Override
    public int getActiveUsersCount() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getActiveUsersCount();
        }
    }

    @Override
    public int getNewUsersThisMonth() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            AnalyticsMapper mapper = session.getMapper(AnalyticsMapper.class);
            return mapper.getNewUsersThisMonth();
        }
    }
}