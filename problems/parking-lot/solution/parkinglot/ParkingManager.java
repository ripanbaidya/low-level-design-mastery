package parkinglot;

import parkingspot.ParkingSpot;
import vehicle.Vehicle;
import vehicle.VehicleSize;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This object oversees the parking lot’s spot allocation, managing the assignment,
 * lookup, and release of ParkingSpot instances.
 * It ensures a Vehicle gets the right spot by checking availability based on size,
 * and updates the system when vehicles leave, keeping parking operations smooth and
 * efficient.
 */
public class ParkingManager {

    /*
     * Key: VehicleSize, Value: list of spots of that size
     */
    private final Map<VehicleSize, CopyOnWriteArrayList<ParkingSpot>> availableSpots;

    /*
     * Bidirectional maps for O(1) lookup in both directions
     */
    private final Map<Vehicle, ParkingSpot> vehicleToSpotMap;
    private final Map<ParkingSpot, Vehicle> spotToVehicleMap;

    public ParkingManager(Map<VehicleSize, CopyOnWriteArrayList<ParkingSpot>> availableSpots) {
        this.availableSpots = availableSpots;

        /*
         * Using ConcurrentHashMap for thread-safe operations across multiple threads
         * without full synchronization
         */
        this.vehicleToSpotMap = new ConcurrentHashMap<>();
        this.spotToVehicleMap = new ConcurrentHashMap<>();
    }

    /**
     * Finds the smallest available spot that fits the vehicle.
     * Checks spots in order of size (smallest to largest) to optimize space usage.
     */
    public ParkingSpot findSpotForVehicle(Vehicle vehicle) {
        VehicleSize vehicleSize = vehicle.getSize();

        for (VehicleSize size : VehicleSize.values()) {
            // Only consider spots that are >= the vehicle's size
            if (size.ordinal() >= vehicleSize.ordinal()) {
                List<ParkingSpot> spots = availableSpots.get(size);
                if (spots != null) {
                    for (ParkingSpot spot : spots) {
                        if (spot.isAvailable()) {
                            return spot;
                        }
                    }
                }
            }
        }

        return null; // No suitable spot found
    }

    /**
     * Parks the vehicle, It involves with finding a spot, marking it occupied,
     * and updating the maps for quick lookups.
     *
     * @return the ParkingSpot assigned to the vehicle, or null if no spot is available.
     */
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = findSpotForVehicle(vehicle);
        if (spot != null) {
            spot.occupy(vehicle);
            vehicleToSpotMap.put(vehicle, spot);
            spotToVehicleMap.put(spot, vehicle);
            availableSpots.get(spot.getVehicleSize()).remove(spot);
            return spot;
        }

        return null;
    }

    /**
     * Unparks the vehicle by retrieving the spot from the map, marking it available again,
     * and updating the maps and available spots list accordingly.
     */
    public void unparkVehicle(Vehicle vehicle) {
        ParkingSpot spot = vehicleToSpotMap.remove(vehicle);
        if (spot != null) {
            spot.vacate();
            spotToVehicleMap.remove(spot);
            availableSpots.get(spot.getVehicleSize()).add(spot);
        }
    }

    /*
     * ******* Note *******
     * These methods provide enhanced functionality for parking lot management.
     * They offer convenient access to parking data and support comprehensive testing scenarios.
     * While not essential for basic operations, they enable better monitoring and debugging capabilities.
     */

    public ParkingSpot getSpotForVehicle(Vehicle vehicle) {
        return vehicleToSpotMap.get(vehicle);
    }

    public Vehicle getVehicleAtSpot(ParkingSpot spot) {
        return spotToVehicleMap.get(spot);
    }

    public int availableSpotsCount(VehicleSize size) {
        List<ParkingSpot> spots = availableSpots.get(size);
        return spots == null ? 0 : spots.size();
    }
}