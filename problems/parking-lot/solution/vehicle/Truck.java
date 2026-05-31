package vehicle;

/**
 * Concrete class of {@code Vehicle} representing a truck.
 * It implements the necessary methods to provide the license plate and size
 * information specific to trucks, which are categorized as large-sized vehicles.
 */
public class Truck implements Vehicle {
    private final String licensePlate;

    public Truck(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.LARGE;
    }
}