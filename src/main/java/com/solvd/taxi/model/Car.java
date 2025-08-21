package com.solvd.taxi.model;

public class Car {
    private int carId;
    private Integer driverId;
    private String model;
    private String plateNumber;
    private Integer typeId;

    public Car() {}

    public Car(String model, String plateNumber, Integer typeId) {
        this.model = model;
        this.plateNumber = plateNumber;
        this.typeId = typeId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", driverId=" + driverId +
                ", model='" + model + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}