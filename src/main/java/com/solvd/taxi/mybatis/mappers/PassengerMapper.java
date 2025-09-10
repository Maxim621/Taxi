package com.solvd.taxi.mybatis.mappers;

import com.solvd.taxi.model.Passenger;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassengerMapper {
    Passenger findById(int id);
    Passenger findByEmail(String email);
    Passenger findByPhone(String phone);
    List<Passenger> findByNamePattern(String name);
    List<Passenger> findAll();
    int getTotalCount();
    int getActiveCount();
    void create(Passenger passenger);
    void update(Passenger passenger);
    void deactivate(@Param("passengerId") int passengerId);
    void activate(@Param("passengerId") int passengerId);
    void delete(int id);
}