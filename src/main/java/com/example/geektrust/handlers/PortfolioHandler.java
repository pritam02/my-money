package com.example.geektrust.handlers;

import com.example.geektrust.Exceptions.PortfolioAlreadyInitializedException;
import com.example.geektrust.Exceptions.RebalanceNotPossibleException;
import com.example.geektrust.constants.PortfolioAllocationForRebalancing;
import com.example.geektrust.constants.PortfolioTransactionTitles;
import com.example.geektrust.datetime.CalendarMonth;
import com.example.geektrust.datetime.MyCalendar;
import com.example.geektrust.helpers.PortfolioTransactionHelper;
import com.example.geektrust.models.PortfolioSnapshot;
import com.example.geektrust.models.Sip;

import java.util.*;

/**
 * a class representing the portfolio of a User
 * stores the portfolio snapshots by month
 * stores the SIP scheduled per month
 * stores the last portfolio snapshot which represents the current value of the portfolio
 */
public class PortfolioHandler {
    private Map<CalendarMonth, List<PortfolioSnapshot>> portfolioTransactionsByMonth;
    private Sip sip;
    private PortfolioSnapshot latestPortfolioSnapshot;
    public PortfolioHandler() {
        portfolioTransactionsByMonth = new TreeMap<>();
        sip = new Sip();
        latestPortfolioSnapshot = null;
    }
    /**
     * Configure SIP for equity, debt and gold for a User
     * @param equityAmount equity Asset Amount to added to the portfolio per month
     * @param debtAmount debt Asset Amount to added to the portfolio per month
     * @param goldAmount gold Asset Amount to added to the portfolio per month
     */
    public void setSip(int equityAmount, int debtAmount, int goldAmount) {
        sip.setEquityAmount(equityAmount);
        sip.setDebtAmount(debtAmount);
        sip.setGoldAmount(goldAmount);
    }
    /**
     * Initialize a User's portfolio with a given amount for each asset class - equity, debt and gold.
     * it is assumed that the initialization needs to be done at the month of January
     * @param equityAmount initial equity Asset Amount to added to the portfolio
     * @param debtAmount initial debt Asset Amount to added to the portfolio
     * @param goldAmount initial gold Asset Amount to added to the portfolio
     */
    public void allocate(int equityAmount, int debtAmount, int goldAmount) throws PortfolioAlreadyInitializedException {
        CalendarMonth firstMonthOfYear = MyCalendar.getInstance().getFirstMonthOfYear();
        if (portfolioTransactionsByMonth.containsKey(firstMonthOfYear)) {
            throw new PortfolioAlreadyInitializedException();
        }
        portfolioTransactionsByMonth.put(firstMonthOfYear, new ArrayList<>());
        PortfolioSnapshot firstPortfolioSnapshot = new PortfolioSnapshot(equityAmount, debtAmount, goldAmount, PortfolioTransactionTitles.ALLOCATION, firstMonthOfYear);
        portfolioTransactionsByMonth.get(firstMonthOfYear).add(firstPortfolioSnapshot);
        latestPortfolioSnapshot = firstPortfolioSnapshot;

    }
    /**
     * check if a transaction exists in the portfolio for a given month
     * @param calendarMonth calendar month for which we need to check for the existence of a transaction
     * @return  boolean returns true if a transaction exists for a given month. else return false
     */
    private boolean isSnapshotAvailableForMonth(CalendarMonth calendarMonth) {
        return PortfolioTransactionHelper.isPortfolioSnapshotAvailableForMonth(portfolioTransactionsByMonth, calendarMonth);
    }
    /**
     * Add Sip amount of each asset class to the User's Portfolio
     * The addition of an amount configured as Sip is considered as a transaction and stored as a snapshot
     * @param calendarMonth calendar month for which we need to add Sip amount for each asset class
     */
    private void applySip(CalendarMonth calendarMonth) {
        PortfolioSnapshot lastPortfolioSnapshotForMonth = PortfolioTransactionHelper.getLastSnapShotForMonth(portfolioTransactionsByMonth, calendarMonth);
        if (lastPortfolioSnapshotForMonth == null) {
            return;
        }
        int equityAmountAfterSip = lastPortfolioSnapshotForMonth.getEquityAmount() + sip.getEquityAmount();
        int debtAmountAfterSip = lastPortfolioSnapshotForMonth.getDebtAmount() + sip.getDebtAmount();
        int goldAmountAfterSip = lastPortfolioSnapshotForMonth.getGoldAmount() + sip.getGoldAmount();
        PortfolioSnapshot portfolioSnapshot = new PortfolioSnapshot(equityAmountAfterSip, debtAmountAfterSip, goldAmountAfterSip, PortfolioTransactionTitles.AFTER_SIP, calendarMonth);
        portfolioTransactionsByMonth.getOrDefault(calendarMonth, new ArrayList<>()).add(portfolioSnapshot);
        latestPortfolioSnapshot = portfolioSnapshot;
    }
    /**
     * apply a percentage change for each asset class for a {@link CalendarMonth}
     * The percentage change is considered as a transaction and is stored as a snapshot for the {@link CalendarMonth}
     * @param equityPercentageChange the percentage change in equity asset class
     * @param debtPercentageChange the percentage change in debt asset class
     * @param goldPercentageChange the percentage change in gold asset class
     * @param calendarMonth the {@link CalendarMonth} for which the percentage change needs to be applied
     */
    private void applyPercentageChangeDueToMarket(double equityPercentageChange, double debtPercentageChange, double goldPercentageChange, CalendarMonth calendarMonth) {
        PortfolioSnapshot lastPortfolioSnapshotForMonth = PortfolioTransactionHelper.getLastSnapShotForMonth(portfolioTransactionsByMonth, calendarMonth);
        if (lastPortfolioSnapshotForMonth == null) {
            return;
        }
        double equityAmountAfterMarketChange = lastPortfolioSnapshotForMonth.getEquityAmount() + ((lastPortfolioSnapshotForMonth.getEquityAmount() * equityPercentageChange) / 100.0);
        double debtAmountAfterMarketChange = lastPortfolioSnapshotForMonth.getDebtAmount() + ((lastPortfolioSnapshotForMonth.getDebtAmount() * debtPercentageChange) / 100.0);
        double goldAmountAftermarketChange = lastPortfolioSnapshotForMonth.getGoldAmount() + ((lastPortfolioSnapshotForMonth.getGoldAmount() * goldPercentageChange)/ 100.0);
        PortfolioSnapshot portfolioSnapshot = new PortfolioSnapshot((int)Math.floor(equityAmountAfterMarketChange), (int)Math.floor(debtAmountAfterMarketChange), (int)Math.floor(goldAmountAftermarketChange), PortfolioTransactionTitles.AFTER_MARKET_CHANGE, calendarMonth);
        portfolioTransactionsByMonth.getOrDefault(calendarMonth, new ArrayList<>()).add(portfolioSnapshot);
        latestPortfolioSnapshot = portfolioSnapshot;
    }
    /**
     * initialise portfolio snapshot for a month by taking the last snapshot of the previous month
     */
    private void updatePortfolioForMonth(CalendarMonth calendarMonth) {
        CalendarMonth previousMonth = MyCalendar.getInstance().getPreviousMonth(calendarMonth);
        if (!isSnapshotAvailableForMonth(previousMonth)) {
            updatePortfolioForMonth(previousMonth);
        }
        int numberOfTransactionsInPreviousMonth = portfolioTransactionsByMonth.get(previousMonth).size();
        PortfolioSnapshot lastPortfolioSnapShotOfPreviousMonth = portfolioTransactionsByMonth.get(previousMonth).get(numberOfTransactionsInPreviousMonth - 1);
        PortfolioSnapshot portfolioSnapshotForCurrentMonth = new PortfolioSnapshot(lastPortfolioSnapShotOfPreviousMonth, PortfolioTransactionTitles.EXISTING, calendarMonth);
        if (!portfolioTransactionsByMonth.containsKey(calendarMonth)) {
            portfolioTransactionsByMonth.put(calendarMonth, new ArrayList<>());
        }
        portfolioTransactionsByMonth.get(calendarMonth).add(portfolioSnapshotForCurrentMonth);
    }
    /**
     * apply a percentage change for each asset class for a Calendar Month
     * the first step is to fetch the {@link CalendarMonth} for the given month in String
     * If transactions does not exist for a {@link CalendarMonth}, initialise the {@link CalendarMonth} with the last Snapshot of the previous month
     * applySip if the current month is not the first month of the year
     * The percentage change is considered as a transaction and is stored as a snapshot for the {@link CalendarMonth}
     * @param equityPercentageChange the percentage change in equity asset class
     * @param debtPercentageChange the percentage change in debt asset class
     * @param goldPercentageChange the percentage change in gold asset class
     * @param month the name of the calendar month in String for which the percentage change needs to be applied
     */
    public void applyChange(double equityPercentageChange, double debtPercentageChange, double goldPercentageChange, String month) {
        CalendarMonth calendarMonth = MyCalendar.getInstance().getCalendarMonthByName(month);
        if (calendarMonth == null) {
            return;
        }
        if (!isSnapshotAvailableForMonth(calendarMonth)) {
            updatePortfolioForMonth(calendarMonth);
        }
        if (!MyCalendar.getInstance().isFirstMonthOfYear(calendarMonth)) {
            applySip(calendarMonth);
        }
        applyPercentageChangeDueToMarket(equityPercentageChange, debtPercentageChange, goldPercentageChange, calendarMonth);
    }
    /**
     * get Portfolio Value for a given month
     * The Portfolio value for a month is the last snapshot that exists for a given month
     * @param month month name in string for which we want to get the Portfolio value
     */
    public PortfolioSnapshot getBalance(String month) {
        CalendarMonth calendarMonth = MyCalendar.getInstance().getCalendarMonthByName(month);
        if (calendarMonth == null) {
            return null;
        }
        return PortfolioTransactionHelper.getLastSnapShotForMonth(portfolioTransactionsByMonth, calendarMonth);
    }
    /**
     * a helper method to display the portfolio
     */
    public void displayPortfolioSnapshotsByMonth() {
        for (Map.Entry<CalendarMonth, List<PortfolioSnapshot>> entry: portfolioTransactionsByMonth.entrySet()) {
            CalendarMonth calendarMonth = entry.getKey();
            for (PortfolioSnapshot portfolioSnapshot: entry.getValue()) {
                System.out.println(calendarMonth.value() + " - " + portfolioSnapshot.getTitle() + "  " + portfolioSnapshot.getEquityAmount() + " "
                        + portfolioSnapshot.getDebtAmount() + " " + portfolioSnapshot.getGoldAmount() + " "
                + portfolioSnapshot.getTotalValue());
            }
        }
    }
    /**
     * Rebalance Portfolio by following a given percentage of allocation per asset
     * The asset allocation percentages are fetched from {@link PortfolioAllocationForRebalancing}
     * The Rebalancing is done based on the total value of the portfolio
     * if Portfolio snapshots are not available for the last 6 months , then rebalancing is not possible
     * @return PortfolioSnapshot. The rebalancing activity is considered as a transaction. The latest snapshot is returned after completing the rebalancing transaction
     */
    public PortfolioSnapshot rebalance() throws RebalanceNotPossibleException  {
        if (latestPortfolioSnapshot == null || portfolioTransactionsByMonth.size() < 6) {
            throw new RebalanceNotPossibleException();
        }
        CalendarMonth lastCalendarMonth = latestPortfolioSnapshot.getMonth();
        int totalPortfolioValue = latestPortfolioSnapshot.getTotalValue();
        int equityAmountAfterRebalancing = (totalPortfolioValue * PortfolioAllocationForRebalancing.EQUITY) / 100;
        int debtAmountAfterRebalancing = (totalPortfolioValue * PortfolioAllocationForRebalancing.DEBT) / 100;
        int goldAmountAfterRebalancing = (totalPortfolioValue * PortfolioAllocationForRebalancing.GOLD) / 100;
        PortfolioSnapshot portfolioSnapshotAfterRebalancing = new PortfolioSnapshot(equityAmountAfterRebalancing, debtAmountAfterRebalancing, goldAmountAfterRebalancing, PortfolioTransactionTitles.REBALANCE, lastCalendarMonth);
        portfolioTransactionsByMonth.get(lastCalendarMonth).add(portfolioSnapshotAfterRebalancing);
        latestPortfolioSnapshot = portfolioSnapshotAfterRebalancing;
        return latestPortfolioSnapshot;
    }
}
