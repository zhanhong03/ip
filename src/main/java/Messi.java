import java.util.Scanner;

public class Messi {
    public static void main(String[] args){
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
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }
            System.out.println(horizontalLine);
        }
    }
}