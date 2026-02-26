package messi.ui;

import java.util.Scanner;

public class Ui {
    private final String horizontalLine = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hola! I'm Messi");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showLine() {
        System.out.println(horizontalLine);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(message);
    }
}