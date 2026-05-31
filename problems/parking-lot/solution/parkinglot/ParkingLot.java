package parkinglot;

import fare.FareCalculator;
import parkingspot.ParkingSpot;
import ticket.Ticket;
import vehicle.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This acts as a facade, providing a central interface to manage the system’s
 * key functionalities - such as vehicle entry, spot assignment, ticketing, and
 * fee calculation.
 * It keeps its logic lightweight by delegating tasks such as spot allocation to the
 * ParkingManager, fee computation to a FareCalculator class, and coordinating the flow
 * of vehicles in and out without handling the details.
 */
public class ParkingLot {
    private final ParkingManager parkingManager;
    private final FareCalculator fareCalculator;

    public ParkingLot(ParkingManager parkingManager, FareCalculator fareCalculator) {
        this.parkingManager = parkingManager;
        this.fareCalculator = fareCalculator;
    }

    /**
     * Parks a vehicle in the parking lot by finding an available spot and generating a ticket.
     *
     * @param vehicle the vehicle to be parked (must not be null)
     * @return a ticket containing parking details if successful, null if no suitable spot is available
     */
    public Ticket parkVehicle(Vehicle vehicle) {
        ParkingSpot spot = parkingManager.parkVehicle(vehicle);
        if (spot != null) {
            System.out.println("[ENTRY] " + vehicle.getLicensePlate()
                    + " parked at Spot #" + spot.getSpotNumber()
                    + " [" + spot.getVehicleSize() + "]");

            return new Ticket(generateTicketId(), vehicle, spot, LocalDateTime.now());
        }
        System.out.println("[ENTRY] No available spot for " + vehicle.getLicensePlate());
        return null;
    }

    /**
     * Unpark the vehicle and calculate the parking fare
     *
     * @param ticket the ticket of the vehicle to unpark
     * @return the fare charged for parking the vehicle
     */
    public BigDecimal unparkVehicle(Ticket ticket) {
        if (ticket == null) {
            System.out.println("[EXIT] Invalid ticket.");
            return BigDecimal.ZERO;
        }
        if (ticket.getExitTime() != null) {
            System.out.println("[EXIT] Ticket already processed: " + ticket.getTicketId());
            return BigDecimal.ZERO;
        }
        ticket.setExitTime(LocalDateTime.now());
        parkingManager.unparkVehicle(ticket.getVehicle());
        BigDecimal fare = fareCalculator.calculateFare(ticket);
        System.out.printf("[EXIT]  %s | Spot #%d | Duration: %s min | Fare: Rs %.2f%n",
                ticket.getVehicle().getLicensePlate(),
                ticket.getParkingSpot().getSpotNumber(),
                ticket.calculateParkingDuration().toPlainString(),
                fare);
        return fare;
    }

    /**
     * Helper method to generate unique ticket ID
     *
     * @return a unique ticket ID
     */
    private String generateTicketId() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}