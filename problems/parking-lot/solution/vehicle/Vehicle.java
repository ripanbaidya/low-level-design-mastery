package vehicle;

/**
 * This object represents a vehicle that needs a spot. It encapsulates details like
 * the license plate and size (small for motorcycles, medium for cars, large for trucks),
 * serving as the foundation for spot assignment and fee calculation.
 */
public interface Vehicle {

    /**
     * Gets the license plate number of the vehicle.
     *
     * @return the license plate
     */
    String getLicensePlate();

    /**
     * Gets the size category of the vehicle.
     *
     * @return the VehicleSize enum value indicating if the vehicle is small,
     * medium, or large
     */
    VehicleSize getSize();
}