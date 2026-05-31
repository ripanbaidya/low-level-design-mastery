package fare;

import ticket.Ticket;
import java.math.BigDecimal;

/**
 * Base fare: rate-per-minute × duration.
 * SMALL  = ₹2/min, MEDIUM = ₹4/min, LARGE = ₹6/min
 */
public class BaseFareStrategy implements FareStrategy {
    private static final BigDecimal SMALL_RATE  = new BigDecimal("2.00");
    private static final BigDecimal MEDIUM_RATE = new BigDecimal("4.00");
    private static final BigDecimal LARGE_RATE  = new BigDecimal("6.00");

    @Override
    public BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare) {
        BigDecimal rate;
        switch (ticket.getVehicle().getSize()) {
            case SMALL  -> rate = SMALL_RATE;
            case MEDIUM -> rate = MEDIUM_RATE;
            case LARGE  -> rate = LARGE_RATE;
            default     -> throw new IllegalArgumentException("Unknown vehicle size: " + ticket.getVehicle().getSize());
        }
        return inputFare.add(rate.multiply(ticket.calculateParkingDuration()));
    }
}