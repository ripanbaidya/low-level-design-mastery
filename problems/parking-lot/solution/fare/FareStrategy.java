package fare;

import ticket.Ticket;

import java.math.BigDecimal;

/**
 * We define FareStrategy as an interface to support a flexible and extensible approach
 * to pricing rules, allowing new strategies (e.g., a WeekendDiscountStrategy) to integrate
 * without altering existing code.
 */
public interface FareStrategy {

    /**
     * Calculates the fare for a given ticket based on the input fare and
     * specific strategy rules.
     *
     * @param ticket    the ticket for which the fare is being calculated
     * @param inputFare the base fare amount before applying strategy-specific calculations
     * @return the calculated fare amount after applying the strategy's pricing rules
     */
    BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare);

}