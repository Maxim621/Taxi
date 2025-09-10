package com.solvd.taxi.mybatis.mappers;

import com.solvd.taxi.model.Driver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DriverMapper {
    Driver findById(int id);
    Driver findByLicense(String licenseNumber);
    List<Driver> findByNamePattern(String name);
    List<Driver> findAll();
    List<Driver> findByRating(double minRating);
    List<Driver> findAvailable();
    int getTotalCount();
    void create(Driver driver);
    void update(Driver driver);
    void updateRating(@Param("driverId") int driverId, @Param("rating") double rating);
    void assignCar(int driverId, int carId);
    void delete(int id);
}