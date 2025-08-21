package com.solvd.taxi.service.interfaces;

import java.sql.SQLException;
import java.util.Map;

public interface AnalyticsService {
    Map<String, Integer> getRidesStatistics() throws SQLException;
    Map<String, Double> getRevenueStatistics() throws SQLException;
    Map<String, Integer> getDriverPerformanceStats() throws SQLException;
    Map<String, Integer> getPassengerActivityStats() throws SQLException;
    Map<String, Integer> getPopularLocations() throws SQLException;
    Map<String, Integer> getRidesByHourOfDay() throws SQLException;
    Map<String, Integer> getRidesByDayOfWeek() throws SQLException;
    double getAverageRideDistance() throws SQLException;
    double getAverageRideDuration() throws SQLException;
    Map<String, Integer> getPaymentMethodsDistribution() throws SQLException;
    Map<Integer, Integer> getRatingDistribution() throws SQLException;
    Map<String, Integer> getCarTypeUsageStats() throws SQLException;
    Map<String, Double> getRevenueByLocation() throws SQLException;
    int getActiveUsersCount() throws SQLException;
    int getNewUsersThisMonth() throws SQLException;
}