package com.example.geektrust.input;

import com.example.geektrust.commands.Command;
import com.example.geektrust.commands.executors.CommandExecutor;
import com.example.geektrust.commands.factories.CommandExecutorFactory;
import com.example.geektrust.output.Printer;
import lombok.AllArgsConstructor;

import java.io.*;

/**
 * A class used to read the contents of a file line by line.
 */
@AllArgsConstructor
public class FileInput {
    private String fileName;
    private CommandExecutorFactory commandExecutorFactory;
    private Printer printer;

    /**
     * Gets CommandExecutor for a particular command.
     * Validates a command using the validate method of the Command Executor
     * if validation is successful, execute the command
     * if validation fails, display an error message to the console
     * @param command Command for which executor has to be fetched.
     */
    private void processCommand(final Command command) {
        CommandExecutor commandExecutor = this.commandExecutorFactory.getCommandExecutor(command);
        if (commandExecutor.validate(command)) {
            commandExecutor.execute(command);
        } else {
            this.printer.printMessage("COMMAND VALIDATION FAILED");
        }
    }

    /**
     * a function to read the contents of a file line by line
     * each line represents a command
     * an instance of {@link Command} class is created and sent for processing
     */
    public void process() throws IOException {
        final File file = new File(this.fileName);
        final BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            return;
        }

        String input = reader.readLine();
        Command command = null;
        while (input != null) {
            System.out.println(input);
            try {
                command = new Command(input);
            } catch (Exception e) {
                command = null;
            }
            if (command != null) {
                processCommand(command);
            }
            input = reader.readLine();

        }
    }
}
