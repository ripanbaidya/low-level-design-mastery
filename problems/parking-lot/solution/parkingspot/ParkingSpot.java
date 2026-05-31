package parkingspot;

import vehicle.Vehicle;
import vehicle.VehicleSize;

/**
 * This object models an individual parking spot in the parking lot.
 * It’s the physical space where a Vehicle parks, ensuring only appropriately sized
 * vehicles can park based on its capacity.
 */
public interface ParkingSpot {

    /**
     * Checks if the parking spot is currently available for parking.
     *
     * @return true if the spot is available, false if it's occupied
     */
    boolean isAvailable();

    /**
     * Occupies the parking spot with the specified vehicle.
     * This method should only be called if the spot is available and
     * the vehicle size is compatible with the spot's capacity.
     *
     * @param vehicle the vehicle that will occupy this parking spot
     */
    void occupy(Vehicle vehicle);

    /**
     * Vacates the parking spot, making it available for other vehicles.
     * This method removes the current vehicle from the spot.
     */
    void vacate();

    /**
     * Gets the unique identifier number for this parking spot.
     *
     * @return the spot number as an integer
     */
    int getSpotNumber();

    /**
     * Gets the vehicle size category that this parking spot can accommodate.
     * This determines what types of vehicles can park in this spot.
     *
     * @return the VehicleSize enum value representing the spot's capacity
     */
    VehicleSize getVehicleSize();
}