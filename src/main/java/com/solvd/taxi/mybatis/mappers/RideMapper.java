package com.solvd.taxi.mybatis.mappers;

import com.solvd.taxi.model.Ride;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RideMapper {
    Ride findById(int id);
    List<Ride> findByPassengerId(int passengerId);
    List<Ride> findByDriverId(int driverId);
    List<Ride> findByStatus(String status);
    List<Ride> findActive();
    List<Ride> findAll();
    List<Ride> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    int getTotalCount();
    int getCountByStatus(String status);
    double calculateRidePrice(int rideId);
    void create(Ride ride);
    void update(Ride ride);
    void updateStatus(@Param("rideId") int rideId, @Param("status") String status);
    void updateStartTime(@Param("rideId") int rideId, @Param("startTime") LocalDateTime startTime);
    void updateEndTime(@Param("rideId") int rideId, @Param("endTime") LocalDateTime endTime);
    void cancel(int rideId);
}