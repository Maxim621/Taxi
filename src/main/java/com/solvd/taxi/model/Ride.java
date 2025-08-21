package com.solvd.taxi.model;

import java.time.LocalDateTime;

public class Ride {
    private int rideId;
    private int passengerId;
    private int driverId;
    private int startLocationId;
    private int endLocationId;
    private int priceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private double distance;

    public Ride() {}

    public Ride(int passengerId, int driverId, int startLocationId, int endLocationId,
                int priceId, double distance) {
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.startLocationId = startLocationId;
        this.endLocationId = endLocationId;
        this.priceId = priceId;
        this.distance = distance;
        this.status = "pending";
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getStartLocationId() {
        return startLocationId;
    }

    public void setStartLocationId(int startLocationId) {
        this.startLocationId = startLocationId;
    }

    public int getEndLocationId() {
        return endLocationId;
    }

    public void setEndLocationId(int endLocationId) {
        this.endLocationId = endLocationId;
    }

    public int getPriceId() {
        return priceId;
    }

    public void setPriceId(int priceId) {
        this.priceId = priceId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "rideId=" + rideId +
                ", passengerId=" + passengerId +
                ", driverId=" + driverId +
                ", startLocationId=" + startLocationId +
                ", endLocationId=" + endLocationId +
                ", priceId=" + priceId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", distance=" + distance +
                '}';
    }
}