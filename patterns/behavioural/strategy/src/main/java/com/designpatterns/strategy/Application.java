package com.designpatterns.strategy;

import com.designpatterns.strategy.context.PaymentContext;
import com.designpatterns.strategy.factory.PaymentStrategyFactory;
import com.designpatterns.strategy.strategy.PaymentStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // SpringApplication.run(StrategyApplication.class, args);

        // 1. Client decides to use Credit Card
        PaymentStrategy cardStrategy = PaymentStrategyFactory
                .createCreditCardStrategy("1234-5678-9876-5432", "Ripan Baidya");

        PaymentContext context = new PaymentContext(cardStrategy);
        context.executePayment(BigDecimal.valueOf(1500.00));

        System.out.println("\n--- User changes mind & switches method ---\n");

        // 2. Dynamic switch to UPI at runtime
        PaymentStrategy upiStrategy = PaymentStrategyFactory.createUpiStrategy("ripan@upi");
        context.setPaymentStrategy(upiStrategy);
        context.executePayment(BigDecimal.valueOf(750.50));
    }
}
