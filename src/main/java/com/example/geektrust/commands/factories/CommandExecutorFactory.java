package com.example.geektrust.commands.factories;

import com.example.geektrust.Exceptions.InvalidCommandException;
import com.example.geektrust.commands.Command;
import com.example.geektrust.commands.constants.InputCommands;
import com.example.geektrust.commands.executors.*;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.output.Printer;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory to get correct Command Executor from a given command.
 */
public class CommandExecutorFactory {
    private Map<String, CommandExecutor> commands = new HashMap<>();
    private Printer printer;
    private PortfolioHandler portfolioHandler;
    public CommandExecutorFactory(final PortfolioHandler portfolioHandler, final Printer printer) {
        this.printer = printer;
        this.portfolioHandler = portfolioHandler;
        commands.put(InputCommands.ALLOCATE, new AllocationCommandExecutor(this.portfolioHandler, this.printer));
        commands.put(InputCommands.SIP, new SipCommandExecutor(this.portfolioHandler, this.printer));
        commands.put(InputCommands.CHANGE, new ChangeCommandExecutor(this.portfolioHandler, this.printer));
        commands.put(InputCommands.BALANCE, new GetBalanceCommandExecutor(this.portfolioHandler, this.printer));
        commands.put(InputCommands.REBALANCE, new RebalanceCommandExecutor(this.portfolioHandler, this.printer));
    }

    /**
     * Gets CommandExecutor for a particular command. It basically uses name of command to
     * fetch its corresponding executor.
     * @param command Command for which executor has to be fetched.
     * @return Command executor.
     */
    public CommandExecutor getCommandExecutor(final Command command) {
        CommandExecutor commandExecutor = null;
        try {
            commandExecutor = commands.get(command.getCommandName());
            if (commandExecutor == null) {
                throw new InvalidCommandException();
            }
        } catch (InvalidCommandException e) {
            this.printer.printMessage("NO EXECUTOR FOUND FOR COMMAND");
            commandExecutor = null;
        }
        return commandExecutor;
    }
}
