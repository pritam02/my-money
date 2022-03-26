package com.example.geektrust.datetime;

/**
 * ENUM representing all the months in a Calendar Year
 */
public enum CalendarMonth {
    JANUARY("JANUARY", 1),
    FEBRUARY("FEBRUARY", 2),
    MARCH("MARCH", 3),
    APRIL("APRIL", 4),
    MAY("MAY", 5),
    JUNE("JUNE", 6),
    JULY("JULY", 7),
    AUGUST("AUGUST", 8),
    SEPTEMBER("SEPTEMBER", 9),
    OCTOBER("OCTOBER", 10),
    NOVEMBER("NOVEMBER", 11),
    DECEMBER("DECEMBER", 12);

    private String month;
    private int index;
    CalendarMonth(String month, int index) {
        this.month = month;
        this.index = index;
    }
    public String value() {
        return this.month;
    }
    public int getIndex() {
        return this.index;
    }
}
