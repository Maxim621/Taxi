package com.solvd.taxi.dao.impl;

import com.solvd.taxi.dao.interfaces.PricingDao;
import com.solvd.taxi.model.Pricing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PricingDaoImpl extends BaseDao implements PricingDao {

    @Override
    public Pricing create(Pricing pricing) throws SQLException {
        String sql = "INSERT INTO Pricing (base_fare, per_km_rate, surge_multiplier) VALUES (?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setDouble(1, pricing.getBaseFare());
            statement.setDouble(2, pricing.getPerKmRate());
            statement.setDouble(3, pricing.getSurgeMultiplier());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating pricing failed, no rows affected.");
            }

            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                pricing.setPriceId(generatedKeys.getInt(1));
            }

            return pricing;
        } finally {
            closeResources(generatedKeys, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Pricing> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Pricing WHERE price_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPricing(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Pricing> findAll() throws SQLException {
        String sql = "SELECT * FROM Pricing";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Pricing> pricings = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                pricings.add(mapResultSetToPricing(resultSet));
            }

            return pricings;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Pricing pricing) throws SQLException {
        String sql = "UPDATE Pricing SET base_fare = ?, per_km_rate = ?, surge_multiplier = ? WHERE price_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);

            statement.setDouble(1, pricing.getBaseFare());
            statement.setDouble(2, pricing.getPerKmRate());
            statement.setDouble(3, pricing.getSurgeMultiplier());
            statement.setInt(4, pricing.getPriceId());

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Pricing WHERE price_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Pricing> findActivePricing() throws SQLException {
        // This would require additional field 'is_active' in the database
        String sql = "SELECT * FROM Pricing LIMIT 1";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPricing(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public List<Pricing> findByBaseFareRange(double minFare, double maxFare) throws SQLException {
        String sql = "SELECT * FROM Pricing WHERE base_fare BETWEEN ? AND ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Pricing> pricings = new ArrayList<>();

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, minFare);
            statement.setDouble(2, maxFare);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                pricings.add(mapResultSetToPricing(resultSet));
            }

            return pricings;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean deactivateAllPricings() throws SQLException {
        // This would require additional field 'is_active' in the database
        String sql = "UPDATE Pricing SET is_active = false";
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(sql) > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public boolean activatePricing(int pricingId) throws SQLException {
        // This would require additional field 'is_active' in the database
        String sql = "UPDATE Pricing SET is_active = true WHERE price_id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, pricingId);
            return statement.executeUpdate() > 0;
        } finally {
            closeResources(statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public Optional<Pricing> getCurrentActivePricing() throws SQLException {
        // This would require additional field 'is_active' in the database
        String sql = "SELECT * FROM Pricing LIMIT 1";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return Optional.of(mapResultSetToPricing(resultSet));
            }
            return Optional.empty();
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Pricing";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } finally {
            closeResources(resultSet, statement, connection);
            releaseConnection(connection);
        }
    }

    private Pricing mapResultSetToPricing(ResultSet resultSet) throws SQLException {
        Pricing pricing = new Pricing();
        pricing.setPriceId(resultSet.getInt("price_id"));
        pricing.setBaseFare(resultSet.getDouble("base_fare"));
        pricing.setPerKmRate(resultSet.getDouble("per_km_rate"));
        pricing.setSurgeMultiplier(resultSet.getDouble("surge_multiplier"));
        return pricing;
    }
}