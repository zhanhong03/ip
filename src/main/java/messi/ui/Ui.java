package messi.ui;

import messi.tasklist.TaskList;
import java.util.Scanner;

/**
 * Handles all interactions with the user (input and output).
 */
public class Ui {
    private final String horizontalLine = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        showLine();
        System.out.println("Hola! I'm Messi");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Prints a standard horizontal line separator.
     */
    public void showLine() {
        System.out.println(horizontalLine);
    }

    /**
     * Reads a full line of text entered by the user.
     *
     * @return The raw command string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Displays a list of tasks that matched a search keyword.
     *
     * @param foundTasks The TaskList containing search matches.
     */
    public void showFoundTasks(TaskList foundTasks) {
        if (foundTasks.size() == 0) {
            System.out.println("No matching tasks found in your list.");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println((i + 1) + "." + foundTasks.getTask(i).toString());
            }
        }
    }

    /**
     * Displays all tasks currently in the main TaskList.
     *
     * @param tasks The main TaskList to be displayed.
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            System.out.println("Your list is currently empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.getTask(i).toString());
            }
        }
    }
}