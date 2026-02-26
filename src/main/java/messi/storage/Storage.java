package messi.storage;

import messi.task.Task;
import messi.task.Todo;
import messi.task.Deadline;
import messi.task.Event;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            File directory = new File(f.getParent());
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }
            f.createNewFile();
            return tasks;
        }

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
                tasks.add(loadedTask);
            }
        }
        fileScanner.close();
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task t : tasks) {
                fw.write(formatTaskForFile(t) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    private String formatTaskForFile(Task t) {
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
}