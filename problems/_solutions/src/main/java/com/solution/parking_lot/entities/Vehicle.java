package com.solution.parking_lot.entities;

import com.solution.parking_lot.enums.VehicleSize;

public interface Vehicle {

    String getLicencePlate();

    VehicleSize getSize();
}
