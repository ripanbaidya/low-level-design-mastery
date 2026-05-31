package parkingspot;

import vehicle.Vehicle;
import vehicle.VehicleSize;

/**
 * Concrete implementation of a {@code ParkingSpot} for oversized vehicles.
 * This spot is designed to accommodate large cars, ensuring that only vehicles of size LARGE
 * can park here.
 */
public class OversizedSpot implements ParkingSpot {
    private final int spotNumber;
    private Vehicle vehicle;

    public OversizedSpot(int spotNumber) {
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
        return VehicleSize.LARGE;
    }
}