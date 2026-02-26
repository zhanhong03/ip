package messi.parser;

import messi.exception.MessiException;

public class Parser {

    public static String[] parse(String input) {
        return input.trim().split(" ", 2);
    }

    public static String[] parseDeadline(String arguments) throws MessiException {
        if (!arguments.contains(" /by ")) {
            throw new MessiException("so... when is it due? (use /by)");
        }
        return arguments.split(" /by ");
    }

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