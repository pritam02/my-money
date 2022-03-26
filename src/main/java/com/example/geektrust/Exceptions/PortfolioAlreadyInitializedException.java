package com.example.geektrust.Exceptions;

public class PortfolioAlreadyInitializedException extends Exception {
    public PortfolioAlreadyInitializedException() {
        super("Portfolio is Already Initialized");
    }
}
