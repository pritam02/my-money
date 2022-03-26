package com.example.geektrust.commands;

import com.example.geektrust.Exceptions.InvalidCommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommandTest {
    @Test
    @DisplayName("Test Allocate Command")
    public void testAllocateCommand() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = new Command("ALLOCATE 6000 3000 1000");
            Assertions.assertEquals(command.getCommandName(), "ALLOCATE");
            Assertions.assertEquals(command.getParams().size(), 3);
        });
    }
    @Test
    @DisplayName("Test SIP Command")
    public void testSipCommand() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = new Command("SIP 2000 1000 500");
            Assertions.assertEquals(command.getCommandName(), "SIP");
            Assertions.assertEquals(command.getParams().size(), 3);
        });
    }
    @Test
    @DisplayName("Test CHANGE Command")
    public void testChangeCommand() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = new Command("CHANGE 4.00% 10.00% 2.00% JANUARY");
            Assertions.assertEquals(command.getCommandName(), "CHANGE");
            Assertions.assertEquals(command.getParams().size(), 4);
        });
    }
    @Test
    @DisplayName("Test BALANCE Command")
    public void testBalanceCommand() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = new Command("BALANCE MARCH");
            Assertions.assertEquals(command.getCommandName(), "BALANCE");
            Assertions.assertEquals(command.getParams().size(), 1);
        });
    }
    @Test
    @DisplayName("Test REBALANCE Command")
    public void testRebalanceCommand() {
        Assertions.assertDoesNotThrow(() -> {
            Command command = new Command("REBALANCE");
            Assertions.assertEquals(command.getCommandName(), "REBALANCE");
            Assertions.assertEquals(command.getParams().size(), 0);
        });
    }
    @Test
    @DisplayName("Test Invalid Command")
    public void testInvalidCommand() {
        Exception exception = Assertions.assertThrows(InvalidCommandException.class, () -> {
            Command command = new Command("");
        });
        Assertions.assertNotNull(exception);
    }
}
