package com.example.geektrust.models;

import com.example.geektrust.constants.PortfolioTransactionTitles;
import com.example.geektrust.datetime.CalendarMonth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PortfolioSnapshotTest {
    @Test
    @DisplayName("Test Portfolio Snapshot Model")
    public void testPortfolioSnapshotModel() {
        PortfolioSnapshot portfolioSnapshot = new PortfolioSnapshot(6000, 3000, 1000, PortfolioTransactionTitles.ALLOCATION, CalendarMonth.JANUARY);
        Assertions.assertEquals(portfolioSnapshot.getEquityAmount(), 6000);
        Assertions.assertEquals(portfolioSnapshot.getDebtAmount(), 3000);
        Assertions.assertEquals(portfolioSnapshot.getGoldAmount(), 1000);
        Assertions.assertEquals(portfolioSnapshot.getTotalValue(), 10000);
        Assertions.assertEquals(portfolioSnapshot.getTitle(), PortfolioTransactionTitles.ALLOCATION);
        Assertions.assertEquals(portfolioSnapshot.getMonth(), CalendarMonth.JANUARY);

        // Test Copy Constructor of Portfolio Snapshot

        PortfolioSnapshot portfolioSnapshot1 = new PortfolioSnapshot(portfolioSnapshot, PortfolioTransactionTitles.EXISTING, CalendarMonth.FEBRUARY);
        Assertions.assertEquals(portfolioSnapshot1.getEquityAmount(), 6000);
        Assertions.assertEquals(portfolioSnapshot1.getDebtAmount(), 3000);
        Assertions.assertEquals(portfolioSnapshot1.getGoldAmount(), 1000);
        Assertions.assertEquals(portfolioSnapshot1.getTotalValue(), 10000);
        Assertions.assertEquals(portfolioSnapshot1.getTitle(), PortfolioTransactionTitles.EXISTING);
        Assertions.assertEquals(portfolioSnapshot1.getMonth(), CalendarMonth.FEBRUARY);
    }
}
