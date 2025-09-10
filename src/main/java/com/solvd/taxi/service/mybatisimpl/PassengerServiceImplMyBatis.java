package com.solvd.taxi.service.mybatisimpl;

import com.solvd.taxi.model.Passenger;
import com.solvd.taxi.mybatis.mappers.PassengerMapper;
import com.solvd.taxi.service.interfaces.PassengerService;
import com.solvd.taxi.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class PassengerServiceImplMyBatis implements PassengerService {
    private static final Logger logger = LogManager.getLogger(PassengerServiceImplMyBatis.class);

    @Override
    public Passenger registerPassenger(Passenger passenger) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            mapper.create(passenger);
            session.commit();
            logger.info("Passenger registered with ID: {}", passenger.getPassengerId());
            return passenger;
        }
    }

    @Override
    public Passenger getPassengerById(int id) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            return mapper.findById(id);
        }
    }

    @Override
    public List<Passenger> getAllPassengers() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public boolean updatePassenger(Passenger passenger) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            mapper.update(passenger);
            session.commit();
            return true;
        }
    }

    @Override
    public boolean deletePassenger(int id) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            mapper.delete(id);
            session.commit();
            return true;
        }
    }

    @Override
    public Passenger findPassengerByEmail(String email) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            return mapper.findByEmail(email);
        }
    }

    @Override
    public Passenger findPassengerByPhone(String phone) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            return mapper.findByPhone(phone);
        }
    }

    @Override
    public List<Passenger> searchPassengersByName(String name) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            return mapper.findByNamePattern(name);
        }
    }

    @Override
    public int getTotalPassengersCount() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            return mapper.getTotalCount();
        }
    }

    @Override
    public boolean deactivatePassenger(int passengerId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            mapper.deactivate(passengerId);
            session.commit();
            return true;
        }
    }

    @Override
    public boolean activatePassenger(int passengerId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            PassengerMapper mapper = session.getMapper(PassengerMapper.class);
            mapper.activate(passengerId);
            session.commit();
            return true;
        }
    }
}