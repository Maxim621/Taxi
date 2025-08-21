package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Car;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    Car create(Car car) throws SQLException;
    Optional<Car> findById(int id) throws SQLException;
    List<Car> findAll() throws SQLException;
    boolean update(Car car) throws SQLException;
    boolean delete(int id) throws SQLException;
    Optional<Car> findByDriverId(int driverId) throws SQLException;
    Optional<Car> findByPlateNumber(String plateNumber) throws SQLException;
    List<Car> findByModel(String model) throws SQLException;
    List<Car> findByType(int typeId) throws SQLException;
    boolean updateDriverAssignment(int carId, Integer newDriverId) throws SQLException;
    int countAll() throws SQLException;
}