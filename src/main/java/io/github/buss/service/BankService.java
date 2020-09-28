package io.github.buss.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import java.util.logging.Logger;

import static java.lang.Thread.sleep;

@Component(value = "bankService")
@Bulkhead(name = "bank")
public class BankService implements Service{

    private static final Logger LOGGER = Logger.getLogger(BankService.class.getName());

    private double currentBalance = 1000;

    @Override
    public void cashOut(double value) throws InterruptedException {
        if (currentBalance < value) {
            throw new RuntimeException("No balance available");
        }
        LOGGER.info("Cash out of: " + value);

        this.currentBalance = this.currentBalance - value;
        sleep(3000);//simulando processamento lento
        LOGGER.info("Done, new balance is: " + currentBalance);
    }

    @Override
    public void deposit(double value) throws InterruptedException {
        this.currentBalance = this.currentBalance + value;
        sleep(3000);//simular processamento
        LOGGER.info("Done, new balance is: " + currentBalance);
    }

    @Override
    public double balance() {
        return currentBalance;
    }

    private String fallbackx(HttpServerErrorException ex) {
        return "Recovered HttpServerErrorException: " + ex.getMessage();
    }

    private String fallbackx(Exception ex) {
        return "Recovered with fallback: " + ex.toString();
    }
}
