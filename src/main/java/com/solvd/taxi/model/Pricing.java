package com.solvd.taxi.model;

public class Pricing {
    private int priceId;
    private double baseFare;
    private double perKmRate;
    private double surgeMultiplier;
    private boolean isActive;

    public Pricing() {
        this.surgeMultiplier = 1.0;
        this.isActive = false;
    }

    public Pricing(double baseFare, double perKmRate) {
        this.baseFare = baseFare;
        this.perKmRate = perKmRate;
        this.surgeMultiplier = 1.0;
        this.isActive = false;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public double getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(double perKmRate) {
        this.perKmRate = perKmRate;
    }

    public double getSurgeMultiplier() {
        return surgeMultiplier;
    }

    public void setSurgeMultiplier(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double calculatePrice(double distance) {
        return baseFare + (perKmRate * distance * surgeMultiplier);
    }

    @Override
    public String toString() {
        return "Pricing{" +
                "priceId=" + priceId +
                ", baseFare=" + baseFare +
                ", perKmRate=" + perKmRate +
                ", surgeMultiplier=" + surgeMultiplier +
                ", isActive=" + isActive +
                '}';
    }
}