package com.solvd.taxi.service.impl;

import com.solvd.taxi.connection.ConnectionPool;
import com.solvd.taxi.dao.interfaces.PassengerDao;
import com.solvd.taxi.dao.impl.PassengerDaoImpl;
import com.solvd.taxi.model.Passenger;
import com.solvd.taxi.service.interfaces.PassengerService;
import com.solvd.taxi.util.Validator;
import com.solvd.taxi.util.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PassengerServiceImpl implements PassengerService {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private final PassengerDao passengerDao;

    public PassengerServiceImpl() {
        this.passengerDao = new PassengerDaoImpl();
    }

    @Override
    public Passenger registerPassenger(Passenger passenger) {
        try {
            if (!Validator.isValidEmail(passenger.getEmail())) {
                throw new IllegalArgumentException("Invalid email format");
            }
            if (!Validator.isValidPhone(passenger.getPhone())) {
                throw new IllegalArgumentException("Invalid phone format");
            }

            return passengerDao.create(passenger);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "passenger registration");
            throw new RuntimeException("Failed to register passenger: " + e.getMessage());
        }
    }

    @Override
    public Passenger getPassengerById(int id) {
        try {
            return passengerDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Passenger not found with id: " + id));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get passenger by id");
            throw new RuntimeException("Failed to get passenger: " + e.getMessage());
        }
    }

    @Override
    public List<Passenger> getAllPassengers() {
        try {
            return passengerDao.findAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get all passengers");
            throw new RuntimeException("Failed to get passengers: " + e.getMessage());
        }
    }

    @Override
    public boolean updatePassenger(Passenger passenger) {
        try {
            return passengerDao.update(passenger);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "update passenger");
            throw new RuntimeException("Failed to update passenger: " + e.getMessage());
        }
    }

    @Override
    public boolean deletePassenger(int id) {
        try {
            return passengerDao.delete(id);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "delete passenger");
            throw new RuntimeException("Failed to delete passenger: " + e.getMessage());
        }
    }

    @Override
    public Passenger findPassengerByEmail(String email) {
        try {
            return passengerDao.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Passenger not found with email: " + email));
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "find passenger by email");
            throw new RuntimeException("Failed to find passenger: " + e.getMessage());
        }
    }

    @Override
    public Passenger findPassengerByPhone(String phone) {
        try {
            Optional<Passenger> passengerOpt = passengerDao.findByPhone(phone);
            if (passengerOpt.isPresent()) {
                return passengerOpt.get();
            } else {
                logger.warn("Passenger not found with phone: {}", phone);
                return null; // instead of throwing exception
            }
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "find passenger by phone");
            throw new RuntimeException("Failed to find passenger: " + e.getMessage());
        }
    }

    @Override
    public List<Passenger> searchPassengersByName(String name) {
        try {
            return passengerDao.findByName(name);
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "search passengers by name");
            throw new RuntimeException("Failed to search passengers: " + e.getMessage());
        }
    }

    @Override
    public int getTotalPassengersCount() {
        try {
            return passengerDao.countAll();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "get passengers count");
            throw new RuntimeException("Failed to get passengers count: " + e.getMessage());
        }
    }

    @Override
    public boolean deactivatePassenger(int passengerId) {
        // Implementation for deactivating passenger account
        Passenger passenger = getPassengerById(passengerId);
        return updatePassenger(passenger);
    }

    @Override
    public boolean activatePassenger(int passengerId) {
        // Implementation for activating passenger account
        Passenger passenger = getPassengerById(passengerId);
        return updatePassenger(passenger);
    }
}