package messi.ui;

import messi.exception.MessiException;
import messi.task.Deadline;
import messi.task.Event;
import messi.task.Task;
import messi.task.Todo;
import messi.storage.Storage;

import java.util.ArrayList;
import java.io.IOException;

public class Messi {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/messi.txt");
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            tasks = storage.load();
        } catch (IOException e) {
            ui.showError("Something went wrong setting up the file: " + e.getMessage());
        }

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    ui.showLine();
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i).toString());
                    }
                } else if (input.startsWith("mark")) {
                    handleMarkUnmark(input, tasks, true);
                    storage.save(tasks);
                } else if (input.startsWith("unmark")) {
                    handleMarkUnmark(input, tasks, false);
                    storage.save(tasks);
                } else if (input.startsWith("delete")) {
                    handleDeletion(input, tasks);
                    storage.save(tasks);
                } else if (input.startsWith("todo")) {
                    if (input.trim().equalsIgnoreCase("todo")) {
                        throw new MessiException("oh no! to do what??");
                    }
                    tasks.add(new Todo(input.substring(5)));
                    printAddedMessage(tasks.get(tasks.size() - 1), tasks.size());
                    storage.save(tasks);
                } else if (input.startsWith("deadline")) {
                    if (!input.contains(" /by ")) {
                        throw new MessiException("so... when is it due?");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    tasks.add(new Deadline(parts[0], parts[1]));
                    printAddedMessage(tasks.get(tasks.size() - 1), tasks.size());
                    storage.save(tasks);
                } else if (input.startsWith("event")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new MessiException("this event got a date?");
                    }
                    String description = input.substring(6).split(" /from ")[0];
                    String from = input.split(" /from ")[1].split(" /to ")[0];
                    String to = input.split(" /to ")[1];
                    tasks.add(new Event(description, from, to));
                    printAddedMessage(tasks.get(tasks.size() - 1), tasks.size());
                    storage.save(tasks);
                } else {
                    throw new MessiException("camera woah woah sorry i don't know what that means");
                }
            } catch (MessiException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    private static void handleDeletion(String input, ArrayList<Task> tasks) throws MessiException {
        try {
            String[] parts = input.split(" ");
            if (parts.length < 2) {
                throw new MessiException("Which task do you want removed?");
            }
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MessiException("We don't have that task number.");
            }
            Task removedTask = tasks.remove(index);
            System.out.println("Noted, I've removed this task:");
            System.out.println(" " + removedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new MessiException("camera woah woah! that task number is not valid.");
        }
    }

    private static void handleMarkUnmark(String input, ArrayList<Task> tasks, boolean isMark) throws MessiException {
        try {
            String[] parts = input.split(" ");
            if (parts.length < 2) {
                throw new MessiException("which number do i mark?");
            }
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new MessiException("that number is not in our system.");
            }
            if (isMark) {
                tasks.get(taskIndex).markAsDone();
                System.out.println("Nice! I've marked this task as done:\n  " + tasks.get(taskIndex));
            } else {
                tasks.get(taskIndex).unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:\n  " + tasks.get(taskIndex));
            }
        } catch (NumberFormatException e) {
            throw new MessiException("that number is not valid");
        }
    }

    private static void printAddedMessage(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task.toString());
        System.out.println("Now you have " + count + " tasks in the list.");
    }
}