package com.example.geektrust.commands.executors;

import com.example.geektrust.Exceptions.PortfolioAlreadyInitializedException;
import com.example.geektrust.commands.Command;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.output.Printer;
import com.example.geektrust.utils.CommonUtil;

import java.util.List;

/**
 * Command to initialise portfolio for the month of January
 */
public class AllocationCommandExecutor extends CommandExecutor {
    public AllocationCommandExecutor(final PortfolioHandler portfolioHandler, final Printer printer) {
        super(portfolioHandler, printer);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Command command) {
        final List<String> params = command.getParams();
        if (params.size() < 3) {
            return false;
        }
        return CommonUtil.isInteger(params.get(0)) && CommonUtil.isInteger(params.get(1)) && CommonUtil.isInteger(params.get(2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {
        final int equityAmount = Integer.parseInt(command.getParams().get(0));
        final int debtAmount = Integer.parseInt(command.getParams().get(1));
        final int goldAmount = Integer.parseInt(command.getParams().get(2));
        try {
            this.portfolioHandler.allocate(equityAmount, debtAmount, goldAmount);
        } catch (PortfolioAlreadyInitializedException e) {
            this.printer.printMessage(e.getMessage());
        }
    }
}
