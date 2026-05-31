package vehicle;

/**
 * Concrete class of {@code Vehicle} representing a motorcycle.
 * It implements the necessary methods to provide the license plate and size
 * information specific to cars, which are categorized as small-sized vehicles.
 */
public class Motorcycle implements Vehicle {
    private final String licensePlate;

    public Motorcycle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL;
    }
}