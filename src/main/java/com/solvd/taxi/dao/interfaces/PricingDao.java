package com.solvd.taxi.dao.interfaces;

import com.solvd.taxi.model.Pricing;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PricingDao {
    Pricing create(Pricing pricing) throws SQLException;
    Optional<Pricing> findById(int id) throws SQLException;
    List<Pricing> findAll() throws SQLException;
    boolean update(Pricing pricing) throws SQLException;
    boolean delete(int id) throws SQLException;
    Optional<Pricing> findActivePricing() throws SQLException;
    List<Pricing> findByBaseFareRange(double minFare, double maxFare) throws SQLException;
    boolean deactivateAllPricings() throws SQLException;
    boolean activatePricing(int pricingId) throws SQLException;
    Optional<Pricing> getCurrentActivePricing() throws SQLException;
    int countAll() throws SQLException;
}