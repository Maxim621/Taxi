package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Passenger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PassengerDao {
    Passenger create(Passenger passenger) throws SQLException;
    Optional<Passenger> findById(int id) throws SQLException;
    List<Passenger> findAll() throws SQLException;
    boolean update(Passenger passenger) throws SQLException;
    boolean delete(int id) throws SQLException;
    Optional<Passenger> findByEmail(String email) throws SQLException;
    Optional<Passenger> findByPhone(String phone) throws SQLException;
    List<Passenger> findByName(String name) throws SQLException;
    int countAll() throws SQLException;
}