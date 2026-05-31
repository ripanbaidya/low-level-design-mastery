package parkingspot;

import vehicle.Vehicle;
import vehicle.VehicleSize;

/**
 * Concrete implementation of a {@code ParkingSpot} for compact vehicles.
 * This spot is designed to accommodate small cars, ensuring that only vehicles of size SMALL
 * can park here.
 */
public class CompactSpot implements ParkingSpot {
    private final int spotNumber;
    private Vehicle vehicle;

    public CompactSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.vehicle = null;
    }

    @Override
    public boolean isAvailable() {
        return vehicle == null;
    }

    @Override
    public void occupy(Vehicle vehicle) {
        if (isAvailable()) {
            this.vehicle = vehicle;
        }
    }

    @Override
    public void vacate() {
        this.vehicle = null;
    }

    @Override
    public int getSpotNumber() {
        return spotNumber;
    }

    @Override
    public VehicleSize getVehicleSize() {
        return VehicleSize.SMALL;
    }
}