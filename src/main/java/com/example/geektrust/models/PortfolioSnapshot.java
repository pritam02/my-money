package com.example.geektrust.models;

import com.example.geektrust.datetime.CalendarMonth;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * a Model representing a Portfolio Transaction for a given user's portfolio
 */
@Getter
@AllArgsConstructor
public class PortfolioSnapshot {
    private int equityAmount;
    private int debtAmount;
    private int goldAmount;
    String title;
    CalendarMonth month;
    public PortfolioSnapshot(PortfolioSnapshot portfolioSnapshot, String title, CalendarMonth calendarMonth) {
        this.equityAmount = portfolioSnapshot.getEquityAmount();
        this.debtAmount = portfolioSnapshot.getDebtAmount();
        this.goldAmount = portfolioSnapshot.getGoldAmount();
        this.title = title;
        this.month = calendarMonth;
    }
    /**
     * Returns the total value of a portfolio by adding the individual values of each asset class
     * @return int. total value of the portfolio
     */
    public int getTotalValue() {
        return this.equityAmount + this.debtAmount + this.goldAmount;
    }
}