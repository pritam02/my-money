package com.example.geektrust.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CommonUtilTest {
    @Test
    @DisplayName("Test For Valid String Check")
    public void testValidString() {
        Assertions.assertTrue(CommonUtil.isValidString("ALLOCATE"));
        Assertions.assertTrue(CommonUtil.isValidString("3000"));
    }
    @Test
    @DisplayName("Test For Invalid String Check")
    public void testInValidString() {
        Assertions.assertFalse(CommonUtil.isValidString(""));
        Assertions.assertFalse(CommonUtil.isValidString(null));
    }
    @Test
    @DisplayName("Test For Valid Integer Check")
    public void checkValidInteger()  {
        Assertions.assertTrue(CommonUtil.isInteger("3000"));
        Assertions.assertTrue(CommonUtil.isInteger("55"));
    }
    @Test
    @DisplayName("Test For Invalid Integer Check")
    public void checkInvalidInteger()  {
        Assertions.assertFalse(CommonUtil.isInteger("abc3000ad"));
        Assertions.assertFalse(CommonUtil.isInteger("BBHSG"));
    }
    @Test
    @DisplayName("Test For Valid Percentage String Check")
    public void checkValidPercentage()  {
        Assertions.assertTrue(CommonUtil.isValidPercentageString("22.55%"));
        Assertions.assertTrue(CommonUtil.isValidPercentageString("0.00%"));
        Assertions.assertTrue(CommonUtil.isValidPercentageString("-10.33%"));
        Assertions.assertTrue(CommonUtil.isValidPercentageString("-0.56%"));
        Assertions.assertTrue(CommonUtil.isValidPercentageString("1.23%"));
        Assertions.assertTrue(CommonUtil.isValidPercentageString("-0.00%"));
    }
    @Test
    @DisplayName("Test For Invalid Percentage String Check")
    public void checkInvalidPercentage()  {
        Assertions.assertFalse(CommonUtil.isValidPercentageString("thrity%"));
        Assertions.assertFalse(CommonUtil.isValidPercentageString("twas"));
        Assertions.assertFalse(CommonUtil.isValidPercentageString("3000"));
        Assertions.assertFalse(CommonUtil.isValidPercentageString("%2000"));
    }
    @Test
    @DisplayName("Test For Extracting Percentage from String")
    public void extractPercentageFromString()  {
        Assertions.assertEquals(CommonUtil.extractPercentageFromString("3.33%"), 3.33);
        Assertions.assertEquals(CommonUtil.extractPercentageFromString("-3.33%"), -3.33);
        Assertions.assertEquals(CommonUtil.extractPercentageFromString("3.33"), 0.0);
        Assertions.assertEquals(CommonUtil.extractPercentageFromString("0.00%"), 0.00);
    }
}
