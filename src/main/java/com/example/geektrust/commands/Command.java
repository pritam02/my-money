package com.example.geektrust.commands;

import com.example.geektrust.Exceptions.InvalidCommandException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model object to represent a input command.
 */
@Getter
public class Command {

    private static final String SPACE = " ";
    private String commandName;
    private List<String> params;

    /**
     * Constructor which takes a command as inputLine
     * @param inputLine Given input command line.
     */
    public Command(final String inputLine) throws InvalidCommandException {
        final List<String> tokensList = Arrays.stream(inputLine.trim().split(SPACE))
                .map(String::trim)
                .filter(token -> (token.length() > 0)).collect(Collectors.toList());
        if (tokensList.size() == 0) {
            throw new InvalidCommandException();
        }

        this.commandName = tokensList.get(0).toUpperCase();
        tokensList.remove(0);
        this.params = tokensList;
    }

}
