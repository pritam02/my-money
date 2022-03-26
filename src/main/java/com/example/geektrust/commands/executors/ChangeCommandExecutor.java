package com.example.geektrust.commands.executors;

import com.example.geektrust.commands.Command;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.output.Printer;
import com.example.geektrust.utils.CommonUtil;

import java.util.List;

/**
 * A class to calculate changes in portfolio for every Month based on the Market trends
 */
public class ChangeCommandExecutor extends CommandExecutor{
    public ChangeCommandExecutor(final PortfolioHandler portfolioHandler, final Printer printer) {
        super(portfolioHandler, printer);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Command command) {
        final List<String> params = command.getParams();
        if (params.size() < 4) {
            return false;
        }
        return CommonUtil.isValidPercentageString(params.get(0)) && CommonUtil.isValidPercentageString(params.get(1)) && CommonUtil.isValidPercentageString(params.get(2));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {
        final double equityChangePercentage = CommonUtil.extractPercentageFromString(command.getParams().get(0));
        final double debtChangePercentage = CommonUtil.extractPercentageFromString(command.getParams().get(1));
        final double goldChangePercentage = CommonUtil.extractPercentageFromString(command.getParams().get(2));
        this.portfolioHandler.applyChange(equityChangePercentage, debtChangePercentage, goldChangePercentage, command.getParams().get(3));
    }
}
