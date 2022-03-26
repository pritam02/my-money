package com.example.geektrust.commands.executors;

import com.example.geektrust.Exceptions.RebalanceNotPossibleException;
import com.example.geektrust.commands.Command;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.models.PortfolioSnapshot;
import com.example.geektrust.output.Printer;
import com.example.geektrust.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Command to rebalance portfolio based on the asset allocation percentages
 */
public class RebalanceCommandExecutor extends CommandExecutor {
    public RebalanceCommandExecutor(final PortfolioHandler portfolioHandler, final Printer printer) {
        super(portfolioHandler, printer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Command command) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {
        try {
            PortfolioSnapshot portfolioSnapshot = this.portfolioHandler.rebalance();
            List<String> outputToPrinter = new ArrayList<>();
            outputToPrinter.add(String.valueOf(portfolioSnapshot.getEquityAmount()));
            outputToPrinter.add(String.valueOf(portfolioSnapshot.getDebtAmount()));
            outputToPrinter.add(String.valueOf(portfolioSnapshot.getGoldAmount()));
            this.printer.printInSameLine(outputToPrinter);
        } catch (RebalanceNotPossibleException e) {
            this.printer.printMessage("CANNOT_REBALANCE");
        }
    }
}
