package com.example.geektrust.commands.executors;

import com.example.geektrust.commands.Command;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.models.PortfolioSnapshot;
import com.example.geektrust.output.Printer;
import com.example.geektrust.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Command to get equity, debt and gold portfolio value for a month
 */
public class GetBalanceCommandExecutor extends CommandExecutor{
    public GetBalanceCommandExecutor(final PortfolioHandler portfolioHandler, final Printer printer) {
        super(portfolioHandler, printer);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Command command) {
        final List<String> params = command.getParams();
        if (params.size() < 1) {
            return false;
        }
        return CommonUtil.isValidString(params.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {
        PortfolioSnapshot portfolioSnapshot = this.portfolioHandler.getBalance(command.getParams().get(0));
        List<String> outputToPrinter = new ArrayList<>();
        outputToPrinter.add(String.valueOf(portfolioSnapshot.getEquityAmount()));
        outputToPrinter.add(String.valueOf(portfolioSnapshot.getDebtAmount()));
        outputToPrinter.add(String.valueOf(portfolioSnapshot.getGoldAmount()));
        this.printer.printInSameLine(outputToPrinter);
    }
}
