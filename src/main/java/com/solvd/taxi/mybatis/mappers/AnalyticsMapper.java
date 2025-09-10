package com.solvd.taxi.mybatis.mappers;

import java.util.List;
import java.util.Map;

public interface AnalyticsMapper {
    Map<String, Integer> getRidesStatistics();
    Map<String, Double> getRevenueStatistics();
    List<Map<String, Object>> getDriverPerformanceStats();
    Map<String, Integer> getPassengerActivityStats();
    Map<String, Integer> getPopularLocations();
    Map<String, Integer> getRidesByHourOfDay();
    Map<String, Integer> getRidesByDayOfWeek();
    double getAverageRideDistance();
    double getAverageRideDuration();
    Map<String, Integer> getPaymentMethodsDistribution();
    Map<Integer, Integer> getRatingDistribution();
    Map<String, Integer> getCarTypeUsageStats();
    Map<String, Double> getRevenueByLocation();
    int getActiveUsersCount();
    int getNewUsersThisMonth();
}