package com.example.geektrust.commands.executors;

import com.example.geektrust.commands.Command;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.output.Printer;

/**
 * Command executor Abstract class.
 * Each Type of command extends the {@link CommandExecutor} abstract Class
 */
public abstract class CommandExecutor {
    protected PortfolioHandler portfolioHandler;
    protected Printer printer;

    public CommandExecutor(final PortfolioHandler portfolioHandler, final Printer printer) {
        this.portfolioHandler = portfolioHandler;
        this.printer = printer;
    }

    /**
     * Validates that whether a command to be executed is valid or not.
     * @param command Command to be validated.
     * @return Boolean indicating whether command is valid or not.
     */
    public abstract boolean validate(Command command);

    /**
     * Executes the command.
     * @param command Command to be executed.
     */
    public abstract void execute(Command command);
}
