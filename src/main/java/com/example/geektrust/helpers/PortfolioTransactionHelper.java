package com.example.geektrust.helpers;

import com.example.geektrust.datetime.CalendarMonth;
import com.example.geektrust.models.PortfolioSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * a helper class exposing apis to do computations on a user's portfolio
 * this helper class helps in separating out most commonly used functionalities in a user's portfolio
 */
public class PortfolioTransactionHelper {
    /**
     * Validates whether a transaction or a portfolio snapshot exists for a given calendar month
     * @param portfolioTransactionsByMonth a map storing portfolio snapshots for all calendar months.
     * @param calendarMonth a calendar month for which we need to check if a snapshot exists or not
     * @return Boolean indicating whether a transaction is present for the calendar month given as input.
     */
    public static boolean isPortfolioSnapshotAvailableForMonth(Map<CalendarMonth, List<PortfolioSnapshot>> portfolioTransactionsByMonth, CalendarMonth calendarMonth) {
        return (portfolioTransactionsByMonth.containsKey(calendarMonth)) && (portfolioTransactionsByMonth.getOrDefault(calendarMonth, new ArrayList<>()).size() > 0);
    }
    /**
     * Fetches the last transaction or portfolio snapshot for a given calendar month
     * @param portfolioTransactionsByMonth a map storing portfolio snapshots for all calendar months.
     * @param calendarMonth a calendar month for which we need fetch the last snapshot
     * @return PortfolioSnapshot. the last snapshot for a given month
     */
    public static PortfolioSnapshot getLastSnapShotForMonth(Map<CalendarMonth, List<PortfolioSnapshot>> portfolioTransactionsByMonth, CalendarMonth calendarMonth) {
       List<PortfolioSnapshot> portfolioSnapshotsForMonth = portfolioTransactionsByMonth.getOrDefault(calendarMonth, new ArrayList<>());
       if (portfolioSnapshotsForMonth.size() == 0) {
           return null;
       }
       return portfolioSnapshotsForMonth.get(portfolioSnapshotsForMonth.size() - 1);
    }
}
