package messi.parser;

import messi.exception.MessiException;

/**
 * Interprets user input and breaks it down into commands and arguments.
 */
public class Parser {

    /**
     * Splits raw user input into a command word and its arguments.
     * * @param input The full line of text entered by the user.
     * @return A String array where index 0 is the command and index 1 is the arguments.
     */
    public static String[] parse(String input) {
        return input.trim().split(" ", 2);
    }

    public static String[] parseDeadline(String arguments) throws MessiException {
        if (!arguments.contains(" /by ")) {
            throw new MessiException("so... when is it due? (use /by)");
        }
        return arguments.split(" /by ");
    }

    /**
     * Parses the arguments specifically for a deadline task.
     * * @param arguments The string containing description and '/by' date.
     * @return An array containing description and date.
     * @throws MessiException If the format is invalid.
     */
    public static String[] parseEvent(String arguments) throws MessiException {
        if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
            throw new MessiException("this event got a date? (use /from and /to)");
        }
        String description = arguments.split(" /from ")[0];
        String from = arguments.split(" /from ")[1].split(" /to ")[0];
        String to = arguments.split(" /to ")[1];
        return new String[]{description, from, to};
    }
}