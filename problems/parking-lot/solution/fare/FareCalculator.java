package fare;

import ticket.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Orchestrates fare calculation by running all strategies in order.
 * Order matters: BaseFare must come before multiplier strategies.
 * Example chain: BaseFareStrategy -> PeakHourFareStrategy
 * Step 1: fare = 0 + (4 x 30 min) = 120
 * Step 2: fare = 120 x 1.5        = 180 (if peak hour)
 */
public class FareCalculator {

    /*
     * Initializes with a list of strategies, setting up the rules to apply
     * during fare calculation.
     */
    private final List<FareStrategy> strategies;

    public FareCalculator(List<FareStrategy> strategies) {
        this.strategies = strategies;
    }

    /**
     * Processes each strategy in sequence, passing the cumulative fare forward
     * to allow each strategy to build upon the previous calculations.
     *
     * @return final fare rounded to 2 decimal places, after applying all strategies
     */
    public BigDecimal calculateFare(Ticket ticket) {
        BigDecimal fare = BigDecimal.ZERO;
        for (FareStrategy strategy : strategies) {
            fare = strategy.calculateFare(ticket, fare);
        }
        return fare.setScale(2, RoundingMode.HALF_UP);
    }
}