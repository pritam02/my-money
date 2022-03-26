package com.example.geektrust.models;

import lombok.Getter;
import lombok.Setter;

/**
 * a Model representing the SIP config for each asset type
 */
@Getter
@Setter
public class Sip {
    int equityAmount;
    int debtAmount;
    int goldAmount;
    public Sip() {
        this.equityAmount = 0;
        this.debtAmount = 0;
        this.goldAmount = 0;
    }
}
