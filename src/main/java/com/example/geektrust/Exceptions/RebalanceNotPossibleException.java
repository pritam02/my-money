package com.example.geektrust.Exceptions;

public class RebalanceNotPossibleException extends Exception{
    public RebalanceNotPossibleException() {
        super("Rebalancing not possible");
    }
}
