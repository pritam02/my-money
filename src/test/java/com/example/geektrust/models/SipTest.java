package com.example.geektrust.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SipTest {
    @Test
    @DisplayName("Test Sip Model")
    public void testSipModel() {
        Sip sip = new Sip();
        Assertions.assertEquals(sip.getEquityAmount(), 0);
        Assertions.assertEquals(sip.getDebtAmount(), 0);
        Assertions.assertEquals(sip.getGoldAmount(), 0);
        sip.setEquityAmount(6000);
        sip.setDebtAmount(3000);
        sip.setGoldAmount(1000);
        Assertions.assertEquals(sip.getEquityAmount(), 6000);
        Assertions.assertEquals(sip.getDebtAmount(), 3000);
        Assertions.assertEquals(sip.getGoldAmount(), 1000);
    }
}
