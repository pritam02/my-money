package com.example.geektrust.datetime;

/**
 * a Singleton instance used to retrieve information related to Months in a Calendar year
 */
public class MyCalendar {
    private static final int TOTAL_MONTHS = 12;
    private static final MyCalendar instance = new MyCalendar();
    private CalendarMonth[] calendarMonths = new CalendarMonth[TOTAL_MONTHS + 1];

    /**
     * stores all the calendar months in an array at index which is equal to the calendar month represented as a number
     */
    private MyCalendar() {
        calendarMonths[1] = CalendarMonth.JANUARY;
        calendarMonths[2] = CalendarMonth.FEBRUARY;
        calendarMonths[3] = CalendarMonth.MARCH;
        calendarMonths[4] = CalendarMonth.APRIL;
        calendarMonths[5] = CalendarMonth.MAY;
        calendarMonths[6] = CalendarMonth.JUNE;
        calendarMonths[7] = CalendarMonth.JULY;
        calendarMonths[8] = CalendarMonth.AUGUST;
        calendarMonths[9] = CalendarMonth.SEPTEMBER;
        calendarMonths[10] = CalendarMonth.OCTOBER;
        calendarMonths[11] = CalendarMonth.NOVEMBER;
        calendarMonths[12] = CalendarMonth.DECEMBER;
    }
    /**
     * get the single instance of {@link MyCalendar}
     */
    public static MyCalendar getInstance() {
        return instance;
    }
    /**
     * get first month of the Year
     */
    public CalendarMonth getFirstMonthOfYear() {
        return calendarMonths[1];
    }
    /**
     * Gets a {@link CalendarMonth} for a given Month represented as String
     * @param name name of the month for which {@link CalendarMonth} needs to be fetched
     * @return CalendarMonth
     */
    public CalendarMonth getCalendarMonthByName(String name) {
        for (int i = 1; i < calendarMonths.length; i++) {
            if (calendarMonths[i].value().equals(name)) {
                return calendarMonths[i];
            }
        }
        return null;
    }
    /**
     * Gets a {@link CalendarMonth} which is the immediate predecessor of a given {@link CalendarMonth}
     * @param calendarMonth the current calendar Month
     * @return CalendarMonth the immediate predecessor the current calendar month
     */
    public CalendarMonth getPreviousMonth(CalendarMonth calendarMonth) {
        return calendarMonths[calendarMonth.getIndex() - 1];
    }
    /**
     * checks if a {@link CalendarMonth} is the first month of the year
     * @param calendarMonth the current calendar Month as input
     * @return Boolean a flag representing if the input represents the first month of the year.
     */
    public Boolean isFirstMonthOfYear(CalendarMonth calendarMonth) {
        return getFirstMonthOfYear().equals(calendarMonth);
    }
}
