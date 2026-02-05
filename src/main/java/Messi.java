import java.util.Scanner;

public class Messi {
    public static void main(String[] args) {
        String horizontalLine = "____________________________________________________________";
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(horizontalLine);
        System.out.println("Hola! I'm Messi");
        System.out.println("What can I do for you?");
        System.out.println(horizontalLine);

        while (true) {
            String input = in.nextLine();
            System.out.println(horizontalLine);

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
                // Get the number after "mark "
                int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:\n  " + tasks[taskIndex]);
            } else if (input.startsWith("unmark")) {
                // Get the number after "unmark "
                int taskIndex = Integer.parseInt(input.split(" ")[1]) - 1;
                tasks[taskIndex].unmarkAsDone();
                System.out.println("OK, I've marked this task as not done yet:\n  " + tasks[taskIndex]);
            } else if (input.startsWith("todo ")) {
                tasks[taskCount] = new Todo(input.substring(5)); //The character index starts from index 5
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by "); //The character index starts from index 9
                tasks[taskCount++] = new Deadline(parts[0], parts[1]);
                printAddedMessage(tasks[taskCount - 1], taskCount);
            } else if (input.startsWith("event ")) {
                String description = input.substring(6).split(" /from ")[0]; //The character index starts from index 6
                String from = input.split(" /from ")[1].split(" /to ")[0];
                String to = input.split(" /to ")[1];
                tasks[taskCount] = new Event(description, from, to);
                taskCount++;
                printAddedMessage(tasks[taskCount - 1], taskCount);
            }
            System.out.println(horizontalLine);
        }
    }

    private static void printAddedMessage(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println(" " + task.toString());
        System.out.println("Now you have " + count + " tasks in the list.");
    }
}