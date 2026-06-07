package com.solution.parking_lot.entities;

import com.solution.parking_lot.enums.VehicleSize;

public class Motorcycle implements Vehicle {
    private final String licensePlate;

    public Motorcycle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicencePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }
}