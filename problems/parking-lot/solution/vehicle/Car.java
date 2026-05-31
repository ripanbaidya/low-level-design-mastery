package vehicle;

/**
 * Concrete class of {@code Vehicle} representing a car.
 * It implements the necessary methods to provide the license plate and size
 * information specific to cars, which are categorized as medium-sized vehicles.
 */
public class Car implements Vehicle {
    private final String licensePlate;

    public Car(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.MEDIUM;
    }
}