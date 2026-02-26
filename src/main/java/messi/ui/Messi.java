package messi.ui;

import messi.exception.MessiException;
import messi.task.Deadline;
import messi.task.Event;
import messi.task.Task;
import messi.task.Todo;
import messi.storage.Storage;
import messi.tasklist.TaskList;
import messi.parser.Parser;

import java.io.IOException;

public class Messi {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/messi.txt");
        TaskList tasks = new TaskList();

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError("Something went wrong setting up the file: " + e.getMessage());
        }

        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();
            ui.showLine();

            String[] parts = Parser.parse(input);
            String command = parts[0].toLowerCase();
            String arguments = (parts.length > 1) ? parts[1] : "";

            try {
                if (command.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    ui.showLine();
                    break;
                } else if (command.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.getTask(i).toString());
                    }
                } else if (command.equals("mark")) {
                    handleMarkUnmark(arguments, tasks, true);
                    storage.save(tasks.getAllTasks());
                } else if (command.equals("unmark")) {
                    handleMarkUnmark(arguments, tasks, false);
                    storage.save(tasks.getAllTasks());
                } else if (command.equals("delete")) {
                    handleDeletion(arguments, tasks);
                    storage.save(tasks.getAllTasks());
                } else if (command.equals("todo")) {
                    if (arguments.isEmpty()) {
                        throw new MessiException("oh no! to do what??");
                    }
                    tasks.addTask(new Todo(arguments));
                    printAddedMessage(tasks.getTask(tasks.size() - 1), tasks.size());
                    storage.save(tasks.getAllTasks());
                } else if (command.equals("deadline")) {
                    String[] deadlineParts = Parser.parseDeadline(arguments);
                    tasks.addTask(new Deadline(deadlineParts[0], deadlineParts[1]));
                    printAddedMessage(tasks.getTask(tasks.size() - 1), tasks.size());
                    storage.save(tasks.getAllTasks());
                } else if (command.equals("event")) {
                    String[] eventParts = Parser.parseEvent(arguments);
                    tasks.addTask(new Event(eventParts[0], eventParts[1], eventParts[2]));
                    printAddedMessage(tasks.getTask(tasks.size() - 1), tasks.size());
                    storage.save(tasks.getAllTasks());
                } else {
                    throw new MessiException("camera woah woah sorry i don't know what that means");
                }
            } catch (MessiException e) {
                ui.showError(e.getMessage());
            }
            ui.showLine();
        }
    }

    private static void handleDeletion(String arguments, TaskList tasks) throws MessiException {
        try {
            if (arguments.isEmpty()) {
                throw new MessiException("Which task do you want removed?");
            }
            int index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MessiException("We don't have that task number.");
            }
            Task removedTask = tasks.getTask(index);
            tasks.deleteTask(index);
            System.out.println("Noted, I've removed this task:");
            System.out.println(" " + removedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new MessiException("camera woah woah! that task number is not valid.");
        }
    }

    private static void handleMarkUnmark(String arguments, TaskList tasks, boolean isMark) throws MessiException {
        try {
            if (arguments.isEmpty()) {
                throw new MessiException("which number do i mark?");
            }
            int taskIndex = Integer.parseInt(arguments) - 1;

            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                throw new MessiException("that number is not in our system.");
            }

            Task task = tasks.getTask(taskIndex);
            if (isMark) {
                task.markAsDone();
                System.out.println("Nice! I've marked this task as done:\n  " + task);
            } else {
                task.unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:\n  " + task);
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