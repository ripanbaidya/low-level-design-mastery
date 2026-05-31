package ticket;

import parkingspot.ParkingSpot;
import vehicle.Vehicle;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * This object represents a parking ticket issued when a Vehicle enters the parking lot.
 * It stores critical details, including the ticket ID, the associated Vehicle,
 * the assigned ParkingSpot, and entry time, which are later used to calculate
 * fees and free up spots upon exit.
 */
public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot parkingSpot,
                  LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = entryTime;
        this.exitTime = null;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    /**
     * Returns parking duration in minutes, Uses exitTime if set,
     * otherwise uses current time.
     *
     * @return Parking duration in minutes as BigDecimal
     */
    public BigDecimal calculateParkingDuration() {
        LocalDateTime end = Objects.requireNonNullElseGet(exitTime, LocalDateTime::now);
        long minutes = Duration.between(entryTime, end).toMinutes();

        // Minimum 1 minute so we never calculate a zero fare
        return new BigDecimal(Math.max(1, minutes));
    }
}