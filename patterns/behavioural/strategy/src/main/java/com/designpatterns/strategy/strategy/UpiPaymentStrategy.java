package com.designpatterns.strategy.strategy;

import java.math.BigDecimal;
import java.util.Objects;

public class UpiPaymentStrategy implements PaymentStrategy {

    private final String upiId;

    public UpiPaymentStrategy(String upiId) {
        this.upiId = Objects.requireNonNull(upiId, "UPI ID cannot be null");
    }

    @Override
    public void pay(BigDecimal amount) {
        System.out.println("Processing UPI payment...");
        System.out.println("UPI ID: " + upiId);
        System.out.println("Amount Paid: ₹" + amount);
    }
}