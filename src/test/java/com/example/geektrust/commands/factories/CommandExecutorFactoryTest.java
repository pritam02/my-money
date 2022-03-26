package com.example.geektrust.commands.factories;

import com.example.geektrust.Exceptions.InvalidCommandException;
import com.example.geektrust.commands.Command;
import com.example.geektrust.commands.executors.*;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.output.Printer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CommandExecutorFactoryTest {
    private CommandExecutorFactory commandExecutorFactory;
    private PortfolioHandler portfolioHandler;
    private Printer printer;

    @BeforeEach
    public void setUp() {
        portfolioHandler = Mockito.mock(PortfolioHandler.class);
        printer = Mockito.mock(Printer.class);
        commandExecutorFactory = new CommandExecutorFactory(portfolioHandler, printer);
    }
    @Test
    @DisplayName("Test Command Executor Factory")
    public void testCommandExecutorFactory() {
        Assertions.assertDoesNotThrow(() -> {
            CommandExecutor allocateCommandExecutor = commandExecutorFactory.getCommandExecutor(new Command("ALLOCATE 6000 3000 1000"));
            Assertions.assertInstanceOf(AllocationCommandExecutor.class, allocateCommandExecutor);
            CommandExecutor sipCommandExecutor = commandExecutorFactory.getCommandExecutor(new Command("SIP 2000 1000 500"));
            Assertions.assertInstanceOf(SipCommandExecutor.class, sipCommandExecutor);
            CommandExecutor changeCommandExecutor = commandExecutorFactory.getCommandExecutor(new Command("CHANGE 4.00% 10.00% 2.00% JANUARY"));
            Assertions.assertInstanceOf(ChangeCommandExecutor.class, changeCommandExecutor);
            CommandExecutor getBalanceCommandExecutor = commandExecutorFactory.getCommandExecutor(new Command("BALANCE MARCH"));
            Assertions.assertInstanceOf(GetBalanceCommandExecutor.class, getBalanceCommandExecutor);
            CommandExecutor rebalanceCommandExecutor = commandExecutorFactory.getCommandExecutor(new Command("REBALANCE"));
            Assertions.assertInstanceOf(RebalanceCommandExecutor.class, rebalanceCommandExecutor);
        });
    }
    @Test
    @DisplayName("Test Command Executor Factory for Invalid Command")
    public void testCommandExecutorFactoryForInvalidCommand() {
        Assertions.assertDoesNotThrow(() -> {
            CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(new Command("TEST 6000 3000 1000"));
            Assertions.assertNull(commandExecutor);
        });
    }
}
