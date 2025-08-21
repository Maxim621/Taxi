package com.solvd.taxi.service.interfaces;

import com.solvd.taxi.model.Passenger;

import java.sql.SQLException;
import java.util.List;

public interface PassengerService {
    Passenger registerPassenger(Passenger passenger) throws SQLException;
    Passenger getPassengerById(int id) throws SQLException;
    List<Passenger> getAllPassengers() throws SQLException;
    boolean updatePassenger(Passenger passenger) throws SQLException;
    boolean deletePassenger(int id) throws SQLException;
    Passenger findPassengerByEmail(String email) throws SQLException;
    Passenger findPassengerByPhone(String phone) throws SQLException;
    List<Passenger> searchPassengersByName(String name) throws SQLException;
    int getTotalPassengersCount() throws SQLException;
    boolean deactivatePassenger(int passengerId) throws SQLException;
    boolean activatePassenger(int passengerId) throws SQLException;
}