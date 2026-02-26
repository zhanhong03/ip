package messi.tasklist;

import messi.task.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

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

public TaskList findTasks(String keyword) {
    TaskList matchingTasks = new TaskList();
    for (Task task : this.tasks) {
        if (task.toString().toLowerCase().contains(keyword.toLowerCase())) {
            matchingTasks.addTask(task);
        }
    }
    return matchingTasks;
}
}