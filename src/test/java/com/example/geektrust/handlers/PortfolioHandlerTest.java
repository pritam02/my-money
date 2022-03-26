package com.example.geektrust.handlers;


import com.example.geektrust.Exceptions.PortfolioAlreadyInitializedException;
import com.example.geektrust.Exceptions.RebalanceNotPossibleException;
import com.example.geektrust.models.PortfolioSnapshot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PortfolioHandlerTest {
    private PortfolioHandler portfolioHandler;

    @BeforeEach
    public void setUp() {
        portfolioHandler = new PortfolioHandler();
    }

    @Test
    @DisplayName("Test Portfolio Handler for First Set of Inputs")
    public void testPortfolioHandlerFirstSet() {
        Assertions.assertDoesNotThrow(() -> {
            portfolioHandler.allocate(6000, 3000, 1000);
            portfolioHandler.setSip(2000, 1000, 500);
            portfolioHandler.applyChange(4.00, 10.00, 2.00, "JANUARY");
            portfolioHandler.applyChange(-10.00, 40.00, 0.00, "FEBRUARY");
            portfolioHandler.applyChange(12.50, 12.50, 12.50, "MARCH");
            portfolioHandler.applyChange(8.00, -3.00, 7.00, "APRIL");
            portfolioHandler.applyChange(13.00, 21.00, 10.50, "MAY");
            portfolioHandler.applyChange(10.00, 8.00, -5.00, "JUNE");
            PortfolioSnapshot portfolioSnapshotForMarch = portfolioHandler.getBalance("MARCH");
            Assertions.assertEquals(portfolioSnapshotForMarch.getEquityAmount(), 10593);
            Assertions.assertEquals(portfolioSnapshotForMarch.getDebtAmount(), 7897);
            Assertions.assertEquals(portfolioSnapshotForMarch.getGoldAmount(), 2272);

            PortfolioSnapshot portfolioSnapshotAfterRebalancing = portfolioHandler.rebalance();
            Assertions.assertEquals(portfolioSnapshotAfterRebalancing.getEquityAmount(), 23619);
            Assertions.assertEquals(portfolioSnapshotAfterRebalancing.getDebtAmount(), 11809);
            Assertions.assertEquals(portfolioSnapshotAfterRebalancing.getGoldAmount(), 3936);
        });

    }
    @Test
    @DisplayName("Test Duplicate Allocation")
    public void testDuplicateAllocation() {
        Assertions.assertThrows(PortfolioAlreadyInitializedException.class, () -> {
            portfolioHandler.allocate(6000, 3000, 1000);
            portfolioHandler.allocate(6000, 3000, 1000);
        });

    }
    @Test
    @DisplayName("Test Duplicate Allocation")
    public void testInValidRebalancing() {
        Assertions.assertDoesNotThrow(() -> {
            portfolioHandler.allocate(6000, 3000, 1000);
            portfolioHandler.setSip(2000, 1000, 500);
            portfolioHandler.applyChange(4.00, 10.00, 2.00, "JANUARY");
            portfolioHandler.applyChange(-10.00, 40.00, 0.00, "FEBRUARY");
            portfolioHandler.applyChange(12.50, 12.50, 12.50, "MARCH");
            portfolioHandler.applyChange(8.00, -3.00, 7.00, "APRIL");
            PortfolioSnapshot portfolioSnapshotForMarch = portfolioHandler.getBalance("MARCH");
            Assertions.assertEquals(portfolioSnapshotForMarch.getEquityAmount(), 10593);
            Assertions.assertEquals(portfolioSnapshotForMarch.getDebtAmount(), 7897);
            Assertions.assertEquals(portfolioSnapshotForMarch.getGoldAmount(), 2272);
        });
        Assertions.assertThrows(RebalanceNotPossibleException.class, () -> {
            PortfolioSnapshot portfolioSnapshotAfterRebalancing = portfolioHandler.rebalance();
        });

    }
}
