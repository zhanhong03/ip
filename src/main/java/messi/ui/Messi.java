package messi.ui;

import messi.exception.MessiException;
import messi.task.Deadline;
import messi.task.Event;
import messi.task.Task;
import messi.task.Todo;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Messi {
    public static void main(String[] args) {
        String horizontalLine = "____________________________________________________________";
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        String filePath = "./data/messi.txt";
        File f = new File(filePath);

        try {
            File directory = new File(f.getParent());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile(); // This is the line that was causing the error!
            } else {
                Scanner fileScanner = new Scanner(f);
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");
                    Task loadedTask = null;

                    if (parts[0].equals("T")) {
                        loadedTask = new Todo(parts[2]);
                    } else if (parts[0].equals("D")) {
                        loadedTask = new Deadline(parts[2], parts[3]);
                    } else if (parts[0].equals("E")) {
                        loadedTask = new Event(parts[2], parts[3], parts[4]);
                    }

                    if (loadedTask != null) {
                        if (parts[1].equals("1")) {
                            loadedTask.markAsDone();
                        }
                        tasks[taskCount++] = loadedTask;
                    }
                }
                fileScanner.close();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong setting up the file: " + e.getMessage());
        }

        System.out.println(horizontalLine);
        System.out.println("Hola! I'm Messi");
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        while (true) {
            String input = in.nextLine();
            System.out.println(horizontalLine);

            try {
                if (input.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(horizontalLine);
                    break;
                } else if (input.equals("list")) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i].toString());
                    }
                } else if (input.startsWith("mark")) {
                    handleMarkUnmark(input, tasks, taskCount, true);
                    saveTasksToFile(filePath, tasks, taskCount);
                } else if (input.startsWith("unmark")) {
                    handleMarkUnmark(input, tasks, taskCount, false);
                    saveTasksToFile(filePath, tasks, taskCount);
                } else if (input.startsWith("todo")) {
                    if (input.trim().equalsIgnoreCase("todo")) {
                        throw new MessiException("oh no! to do what??");
                    }
                    tasks[taskCount++] = new Todo(input.substring(5));
                    printAddedMessage(tasks[taskCount - 1], taskCount);
                    saveTasksToFile(filePath, tasks, taskCount);
                } else if (input.startsWith("deadline")) {
                    if (!input.contains(" /by ")) {
                        throw new MessiException("so... when is it due?");
                    }
                    String[] parts = input.substring(9).split(" /by ");
                    tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                    printAddedMessage(tasks[taskCount - 1], taskCount);
                    saveTasksToFile(filePath, tasks, taskCount);
                } else if (input.startsWith("event")) {
                    if (!input.contains(" /from ") || !input.contains(" /to ")) {
                        throw new MessiException("this event got a date?");
                    }
                    String description = input.substring(6).split(" /from ")[0];
                    String from = input.split(" /from ")[1].split(" /to ")[0];
                    String to = input.split(" /to ")[1];
                    tasks[taskCount++] = new Event(description, from, to);
                    printAddedMessage(tasks[taskCount - 1], taskCount);
                    saveTasksToFile(filePath, tasks, taskCount);
                } else {
                    // Handle unknown commands
                    throw new MessiException("camera woah woah sorry i don't know what that means");
                }
            } catch (MessiException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(horizontalLine);
        }
    }

    private static void saveTasksToFile(String filePath, Task[] tasks, int taskCount) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < taskCount; i++) {
                fw.write(formatTaskForFile(tasks[i]) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    private static String formatTaskForFile(Task t) {
        String type = t instanceof Todo ? "T" : t instanceof Deadline ? "D" : "E";
        int status = t.isDone() ? 1 : 0;
        String desc = t.getDescription();

        if (t instanceof Deadline) {
            return type + "," + status + "," + desc + "," + ((Deadline) t).getBy();
        } else if (t instanceof Event) {
            return type + "," + status + "," + desc + "," + ((Event) t).getFrom() + "," + ((Event) t).getTo();
        }
        return type + "," + status + "," + desc;
    }

    private static void handleMarkUnmark(String input, Task[] tasks, int taskCount, boolean isMark) throws
            MessiException {
        try {
            String[] parts = input.split(" ");
            if (parts.length < 2) {
                throw new MessiException("which number do i mark?"); //no number
            }
            int taskIndex = Integer.parseInt(parts[1]) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new MessiException("that number is not in our system."); //number is out of bounds
            }
            if (isMark) {
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:\n  " + tasks[taskIndex]);
            } else {
                tasks[taskIndex].unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:\n  " + tasks[taskIndex]);
            }
        } catch (NumberFormatException e) {
            throw new MessiException("that number is not valid"); //non integer input
        }
    }


    private static void printAddedMessage(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task.toString());
        System.out.println("Now you have " + count + " tasks in the list.");
    }
}