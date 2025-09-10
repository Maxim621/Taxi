package com.solvd.taxi.service.mybatisimpl;

import com.solvd.taxi.model.Driver;
import com.solvd.taxi.mybatis.mappers.DriverMapper;
import com.solvd.taxi.service.interfaces.DriverService;
import com.solvd.taxi.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class DriverServiceImplMyBatis implements DriverService {
    private static final Logger logger = LogManager.getLogger(DriverServiceImplMyBatis.class);

    @Override
    public Driver registerDriver(Driver driver) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            mapper.create(driver);
            session.commit();
            logger.info("Driver registered with ID: {}", driver.getDriverId());
            return driver;
        }
    }

    @Override
    public Driver getDriverById(int id) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.findById(id);
        }
    }

    @Override
    public List<Driver> getAllDrivers() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public boolean updateDriver(Driver driver) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            mapper.update(driver);
            session.commit();
            return true;
        }
    }

    @Override
    public boolean deleteDriver(int id) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            mapper.delete(id);
            session.commit();
            return true;
        }
    }

    @Override
    public Driver findDriverByLicense(String licenseNumber) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.findByLicense(licenseNumber);
        }
    }

    @Override
    public List<Driver> searchDriversByName(String name) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.findByNamePattern(name);
        }
    }

    @Override
    public List<Driver> getDriversByRating(double minRating) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.findByRating(minRating);
        }
    }

    @Override
    public boolean updateDriverRating(int driverId, double newRating) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            mapper.updateRating(driverId, newRating);
            session.commit();
            return true;
        }
    }

    @Override
    public int getTotalDriversCount() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.getTotalCount();
        }
    }

    @Override
    public List<Driver> getAvailableDrivers() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            return mapper.findAvailable();
        }
    }

    @Override
    public boolean assignCarToDriver(int driverId, int carId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DriverMapper mapper = session.getMapper(DriverMapper.class);
            mapper.assignCar(driverId, carId);
            session.commit();
            return true;
        }
    }
}