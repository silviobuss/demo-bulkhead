package io.github.buss.service;

public interface Service {

    void cashOut(double value) throws InterruptedException;
    void deposit(double value) throws InterruptedException;
    double balance();
}
