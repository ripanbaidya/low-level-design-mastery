package parkingspot;

import vehicle.Vehicle;
import vehicle.VehicleSize;

/**
 * Concrete implementation of a {@code ParkingSpot} for regular vehicles.
 * This spot is designed to accommodate medium cars, ensuring that only vehicles of size MEDIUM
 * can park here.
 */
public class RegularSpot implements ParkingSpot {
    private final int spotNumber;
    private Vehicle vehicle;

    public RegularSpot(int spotNumber) {
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
        return VehicleSize.MEDIUM;
    }
}