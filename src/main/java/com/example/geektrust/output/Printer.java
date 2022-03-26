package com.example.geektrust.output;

import com.example.geektrust.utils.CommonUtil;

import java.util.List;

/**
 * A class to print data to the console
 */
public class Printer {
    /**
     * prints list of Strings in a single line
     * @param outputStrings List of Strings to be displayed
     */
    public void printInSameLine(List<String> outputStrings) {
        if (outputStrings == null || outputStrings.size() == 0) {
            return;
        }
        for (String outputString: outputStrings) {
            System.out.print(outputString + " ");
        }
        System.out.println("");
    }
    /**
     * prints list of Strings separate lines
     * @param outputStrings List of Strings to be displayed.
     */
    public void printInSeparateLines(List<String> outputStrings) {
        if (outputStrings == null || outputStrings.size() == 0) {
            return;
        }
        for (String outputString: outputStrings) {
            System.out.println(outputString);
        }
    }
    /**
     * Prints a message to the console
     * @param message a message to be displayed in the console
     */
    public void printMessage(String message) {
        if (!CommonUtil.isValidString(message)) {
            return;
        }
        System.out.println(message);
    }
}
