package com.example.geektrust;

import com.example.geektrust.commands.factories.CommandExecutorFactory;
import com.example.geektrust.handlers.PortfolioHandler;
import com.example.geektrust.input.FileInput;
import com.example.geektrust.output.Printer;

/**
 * Main class to start program execution
 */
public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer();
        PortfolioHandler portfolioHandler = new PortfolioHandler();
        CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(portfolioHandler, printer);
        FileInput fileInput = new FileInput(args[0], commandExecutorFactory, printer);
        try {
            fileInput.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}