package messi.tasklist;

import messi.task.Task;
import java.util.ArrayList;

/**
 * Represents the list of tasks and provides methods to manipulate the tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     * * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the list based on its index.
     * * @param index The index of the task to be removed.
     * @return The task that was removed.
     */
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

    /**
     * Returns a TaskList containing tasks that match the search keyword.
     * * @param keyword The string to search for.
     * @return A TaskList containing matching tasks.
     */
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