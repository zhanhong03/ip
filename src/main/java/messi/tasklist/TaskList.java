package messi.tasklist;

import messi.task.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    // Constructor for when we load existing tasks from a file
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    // Constructor for starting with an empty list
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}