package core;

import models.Task;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManagerImpl implements TaskManager {
    LinkedHashMap<String, Task> tasksById = new LinkedHashMap<>();
    LinkedHashSet<Task> pendingTasks = new LinkedHashSet<>();
    Map<String, Task> executedTasks = new HashMap<>();

    @Override
    public void addTask(Task task) {
        tasksById.put(task.getId(), task);
        pendingTasks.add(task);
    }

    @Override
    public boolean contains(Task task) {
        return tasksById.containsKey(task.getId());
    }

    @Override
    public int size() {
        return tasksById.size();
    }

    @Override
    public Task getTask(String taskId) {
        Task task = tasksById.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException();
        }
        return task;
    }

    @Override
    public void deleteTask(String taskId) {
        Task task = tasksById.remove(taskId);
        if (task == null) {
            throw new IllegalArgumentException();
        }
        pendingTasks.remove(task);
        executedTasks.remove(task.getId());
    }

    @Override
    public Task executeTask() {
        if (pendingTasks.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Task task = pendingTasks.iterator().next();
        pendingTasks.remove(task);
        executedTasks.put(task.getId(), task);
        return task;
    }

    @Override
    public void rescheduleTask(String taskId) {
        Task task = tasksById.remove(taskId);
        if (task == null) {
            throw new IllegalArgumentException();
        }
        pendingTasks.add(task);
    }

    @Override
    public Iterable<Task> getDomainTasks(String domain) {
        List<Task> collect = pendingTasks.stream().filter(task -> task.getDomain().equals(domain))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return collect;
    }

    @Override
    public Iterable<Task> getTasksInEETRange(int lowerBound, int upperBound) {
        return pendingTasks
                .stream()
                .filter(task -> task.getEstimatedExecutionTime() >= lowerBound
                        && task.getEstimatedExecutionTime() <= upperBound)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Task> getAllTasksOrderedByEETThenByName() {
        return tasksById
                .values()
                .stream()
                .sorted(Comparator.comparing(Task::getEstimatedExecutionTime, Comparator.reverseOrder())
                        .thenComparing(t -> t.getName().length()))
                .collect(Collectors.toList());
    }
}
