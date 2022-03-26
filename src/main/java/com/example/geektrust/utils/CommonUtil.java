package com.example.geektrust.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A common class which contains utility methods to be used throughout the application
 */
public class CommonUtil {
    /**
     * Validates that whether a string is an integer or not. Does this by checking if it is
     * successfully able to convert the input string to integer or not.
     * @param input Input string.
     * @return Boolean indicating whether an input is integer or not.
     */
    public static boolean isInteger(final String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
    /**
     * Validates that whether a string is valid or not. Does this by checking if String is not null or length is greater than zero
     * @param str Input string.
     * @return Boolean indicating whether an input is integer or not.
     */
    public static boolean isValidString(final String str) {
        return str != null && str.length() > 0;
    }

    /**
     * Validates that whether a string is a valid percentage or not. Does this by checking if String is matching the regex
     * @param input Input string.
     * @return Boolean indicating whether an input is integer or not.
     */
    public static boolean isValidPercentageString(String input) {
        final String regex = "^-?\\d+(\\.\\d+)?%$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    /**
     * returns a percentage number formed by all the integers present in the string
     * @param input Input string.
     * @return double a number formed by all the digits present in the string
     */
    public static double extractPercentageFromString(String input) {
        if (!isValidPercentageString(input)) {
            return 0.0;
        }
        String numberOnly = input.substring(0, input.length() - 1);
        return Double.parseDouble(numberOnly);
    }

}
