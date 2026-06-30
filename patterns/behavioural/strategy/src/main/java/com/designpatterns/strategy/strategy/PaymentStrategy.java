package com.designpatterns.strategy.strategy;

import java.math.BigDecimal;

/**
 * Strategy Interface
 * Defines the contract for different payment algorithms.
 */
public interface PaymentStrategy {

    /**
     * Process payment for the given amount.
     *
     * @param amount amount to be paid
     */
    void pay(BigDecimal amount);
}