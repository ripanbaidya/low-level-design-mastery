import fare.BaseFareStrategy;
import fare.FareCalculator;
import fare.PeakHourFareStrategy;
import parkingspot.*;
import parkinglot.ParkingLot;
import parkinglot.ParkingManager;
import ticket.Ticket;
import vehicle.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {

        // 1. Set up parking spots
        CopyOnWriteArrayList<ParkingSpot> compactSpots = new CopyOnWriteArrayList<>(Arrays.asList(
                new CompactSpot(1),
                new CompactSpot(2)
        ));
        CopyOnWriteArrayList<ParkingSpot> regularSpots = new CopyOnWriteArrayList<>(Arrays.asList(
                new RegularSpot(3),
                new RegularSpot(4)
        ));
        CopyOnWriteArrayList<ParkingSpot> oversizedSpots = new CopyOnWriteArrayList<>(Arrays.asList(
                new OversizedSpot(5)
        ));

        Map<VehicleSize, CopyOnWriteArrayList<ParkingSpot>> spotMap = new EnumMap<>(VehicleSize.class);
        spotMap.put(VehicleSize.SMALL, compactSpots);
        spotMap.put(VehicleSize.MEDIUM, regularSpots);
        spotMap.put(VehicleSize.LARGE, oversizedSpots);

        // 2. Fare calculator — order matters: base first, then peak multiplier
        FareCalculator fareCalculator = new FareCalculator(Arrays.asList(
                new BaseFareStrategy(),
                new PeakHourFareStrategy()
        ));

        // 3. Create parking lot (Facade)
        ParkingManager parkingManager = new ParkingManager(spotMap);
        ParkingLot parkingLot = new ParkingLot(parkingManager, fareCalculator);

        out.println("===== PARKING LOT SIMULATION =====\n");

        // 4. Park vehicles
        Vehicle motorcycle = new Motorcycle("MH-01-AA-1111");
        Vehicle car1 = new Car("MH-02-BB-2222");
        Vehicle car2 = new Car("MH-03-CC-3333");
        Vehicle truck = new Truck("MH-04-DD-4444");
        Vehicle extraCar = new Car("MH-05-EE-5555");

        Ticket motoTicket = parkingLot.parkVehicle(motorcycle);
        Ticket car1Ticket = parkingLot.parkVehicle(car1);
        Ticket car2Ticket = parkingLot.parkVehicle(car2);
        Ticket truckTicket = parkingLot.parkVehicle(truck);
        Ticket extraTicket = parkingLot.parkVehicle(extraCar);

        out.println();

        // 5. Simulate 2-second stay (duration shows as 1 min minimum)
        out.println("[INFO] Simulating brief stay...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        out.println();

        // 6. Exit vehicles
        parkingLot.unparkVehicle(motoTicket);
        parkingLot.unparkVehicle(car1Ticket);
        parkingLot.unparkVehicle(truckTicket);

        out.println();

        // 7. Edge case: double exit
        out.println("[TEST] Double exit same ticket:");
        parkingLot.unparkVehicle(car1Ticket);

        // 8. Edge case: null ticket
        out.println("[TEST] Null ticket:");
        parkingLot.unparkVehicle(null);

        out.println("\n===== DONE =====");
    }
}