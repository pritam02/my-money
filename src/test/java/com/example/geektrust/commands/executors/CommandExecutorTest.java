package com.example.geektrust.commands.executors;

import com.example.geektrust.commands.Command;
import com.example.geektrust.constants.PortfolioTransactionTitles;
import com.example.geektrust.datetime.CalendarMonth;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.models.PortfolioSnapshot;
import com.example.geektrust.output.Printer;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class CommandExecutorTest {
    private PortfolioHandler portfolioHandler;
    private Printer printer;
    private AllocationCommandExecutor allocationCommandExecutor;
    private SipCommandExecutor sipCommandExecutor;
    private ChangeCommandExecutor changeCommandExecutor;
    private GetBalanceCommandExecutor getBalanceCommandExecutor;
    private RebalanceCommandExecutor rebalanceCommandExecutor;
    @BeforeEach
    public void setUp() {
        portfolioHandler = Mockito.mock(PortfolioHandler.class);
        printer = Mockito.mock(Printer.class);
        allocationCommandExecutor = new AllocationCommandExecutor(portfolioHandler, printer);
        sipCommandExecutor = new SipCommandExecutor(portfolioHandler, printer);
        changeCommandExecutor = new ChangeCommandExecutor(portfolioHandler, printer);
        getBalanceCommandExecutor = new GetBalanceCommandExecutor(portfolioHandler, printer);
        rebalanceCommandExecutor = new RebalanceCommandExecutor(portfolioHandler, printer);
    }
    @Test
    @DisplayName("Test validation for all command executors")
    public void testValidationForAllCommandExecutors() {
        Assertions.assertDoesNotThrow(() -> {
            //test validation of allocationCommandExecutor
            Assertions.assertTrue(allocationCommandExecutor.validate(new Command("ALLOCATE 6000 3000 1000")));
            Assertions.assertFalse(allocationCommandExecutor.validate(new Command("ALLOCATE 6000 3000")));
            Assertions.assertFalse(allocationCommandExecutor.validate(new Command("ALLOCATE 6000 3000 22BBAA")));

            //test validation of sipCommandExecutor
            Assertions.assertTrue(sipCommandExecutor.validate(new Command("SIP 2000 1000 500")));
            Assertions.assertFalse(sipCommandExecutor.validate(new Command("SIP 2000 1000")));
            Assertions.assertFalse(sipCommandExecutor.validate(new Command("SIP 2000 12TT 500")));

            //test validation of changeCommandExecutor
            Assertions.assertTrue(changeCommandExecutor.validate(new Command("CHANGE 4.00% 10.00% 2.00% JANUARY")));
            Assertions.assertFalse(changeCommandExecutor.validate(new Command("CHANGE 4.00% 10.00% 2.00%")));
            Assertions.assertFalse(changeCommandExecutor.validate(new Command("CHANGE 4.00% 10.00 2.00 JANUARY")));

            //test validation of changeCommandExecutor
            Assertions.assertTrue(getBalanceCommandExecutor.validate(new Command("BALANCE MARCH")));
            Assertions.assertFalse(getBalanceCommandExecutor.validate(new Command("BALANCE")));


            //test validation of changeCommandExecutor
            Assertions.assertTrue(rebalanceCommandExecutor.validate(new Command("REBALANCE")));

        });
    }
    @Test
    @DisplayName("Test Execute Method of all Command Executor")
    public void testExecuteMethodOfAllCommandExecutors() {
        Assertions.assertDoesNotThrow(() -> {
            allocationCommandExecutor.execute(new Command("ALLOCATE 6000 3000 1000"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).allocate(6000, 3000, 1000);

            sipCommandExecutor.execute(new Command("SIP 2000 1000 500"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).setSip(2000, 1000, 500);

            changeCommandExecutor.execute(new Command("CHANGE 4.00% 10.00% 2.00% JANUARY"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).applyChange(4.00, 10.00, 2.00, "JANUARY");
            changeCommandExecutor.execute(new Command("CHANGE -10.00% 40.00% 0.00% FEBRUARY"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).applyChange(-10.00, 40.00, 0.00, "FEBRUARY");
            changeCommandExecutor.execute(new Command("CHANGE 12.50% 12.50% 12.50% MARCH"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).applyChange(12.50, 12.50, 12.50, "MARCH");

            Mockito.when(portfolioHandler.getBalance("MARCH")).thenReturn(new PortfolioSnapshot(10593, 7897,  2272, PortfolioTransactionTitles.AFTER_MARKET_CHANGE, CalendarMonth.MARCH));
            getBalanceCommandExecutor.execute(new Command("BALANCE MARCH"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).getBalance("MARCH");

            changeCommandExecutor.execute(new Command("CHANGE 8.00% -3.00% 7.00% APRIL"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).applyChange(8.00, -3.00, 7.00, "APRIL");
            changeCommandExecutor.execute(new Command("CHANGE 13.00% 21.00% 10.50% MAY"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).applyChange(13.00, 21.00, 10.50, "MAY");
            changeCommandExecutor.execute(new Command("CHANGE 10.00% 8.00% -5.00% JUNE"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).applyChange(10.00, 8.00, -5.00, "JUNE");

            Mockito.when(portfolioHandler.rebalance()).thenReturn(new PortfolioSnapshot(23619, 11809, 3936, PortfolioTransactionTitles.REBALANCE, CalendarMonth.JUNE));
            rebalanceCommandExecutor.execute(new Command("REBALANCE"));
            Mockito.verify(portfolioHandler, Mockito.times(1)).rebalance();
        });
    }
}
