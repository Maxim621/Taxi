package com.solvd.taxi.service.mybatisimpl;

import com.solvd.taxi.model.Ride;
import com.solvd.taxi.mybatis.mappers.RideMapper;
import com.solvd.taxi.service.interfaces.RideService;
import com.solvd.taxi.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class RideServiceImplMyBatis implements RideService {
    private static final Logger logger = LogManager.getLogger(RideServiceImplMyBatis.class);

    @Override
    public Ride createRide(Ride ride) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            mapper.create(ride);
            session.commit();
            logger.info("Ride created with ID: {}", ride.getRideId());
            return ride;
        }
    }

    @Override
    public Ride getRideById(int id) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.findById(id);
        }
    }

    @Override
    public List<Ride> getAllRides() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public boolean updateRide(Ride ride) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            mapper.update(ride);
            session.commit();
            return true;
        }
    }

    @Override
    public boolean cancelRide(int rideId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            mapper.cancel(rideId);
            session.commit();
            return true;
        }
    }

    @Override
    public List<Ride> getRidesByPassenger(int passengerId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.findByPassengerId(passengerId);
        }
    }

    @Override
    public List<Ride> getRidesByDriver(int driverId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.findByDriverId(driverId);
        }
    }

    @Override
    public List<Ride> getRidesByStatus(String status) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.findByStatus(status);
        }
    }

    @Override
    public boolean startRide(int rideId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            mapper.updateStatus(rideId, "ongoing");
            mapper.updateStartTime(rideId, LocalDateTime.now());
            session.commit();
            logger.info("Ride {} started", rideId);
            return true;
        }
    }

    @Override
    public boolean completeRide(int rideId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            mapper.updateStatus(rideId, "completed");
            mapper.updateEndTime(rideId, LocalDateTime.now());
            session.commit();
            logger.info("Ride {} completed", rideId);
            return true;
        }
    }

    @Override
    public List<Ride> getActiveRides() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.findActive();
        }
    }

    @Override
    public double calculateRidePrice(int rideId) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.calculateRidePrice(rideId);
        }
    }

    @Override
    public int getTotalRidesCount() throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.getTotalCount();
        }
    }

    @Override
    public int getRidesCountByStatus(String status) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            return mapper.getCountByStatus(status);
        }
    }

    @Override
    public List<Ride> getRidesByDateRange(String startDate, String endDate) throws SQLException {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RideMapper mapper = session.getMapper(RideMapper.class);
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            return mapper.findByDateRange(start, end);
        }
    }
}