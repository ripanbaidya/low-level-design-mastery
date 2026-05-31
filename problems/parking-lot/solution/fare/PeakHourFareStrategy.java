package fare;

import ticket.Ticket;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Peak-hour surcharge: 50% extra if vehicle entered during peak hours.
 * Peak hours: 07:00–10:00 and 16:00–19:00 <br>
 * NOTE: This multiplies the TOTAL fare computed so far (including base).
 * So it must be added AFTER BaseFareStrategy in the FareCalculator list.
 */
public class PeakHourFareStrategy implements FareStrategy {
    private static final BigDecimal PEAK_MULTIPLIER = new BigDecimal("1.5");

    @Override
    public BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare) {
        if (isPeakHour(ticket.getEntryTime())) {
            return inputFare.multiply(PEAK_MULTIPLIER);
        }
        return inputFare;
    }

    private boolean isPeakHour(LocalDateTime time) {
        int hour = time.getHour();
        return (hour >= 7 && hour <= 10) || (hour >= 16 && hour <= 19);
    }
}