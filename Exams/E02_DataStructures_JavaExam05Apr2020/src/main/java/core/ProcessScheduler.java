package core;

import model.Task;
import shared.Scheduler;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessScheduler implements Scheduler {

    Deque<Task> tasks;
    //fixme: some fixes are needed
    public ProcessScheduler() {
        this.tasks = new ArrayDeque<>();
    }

    @Override
    public void add(Task task) {
        this.tasks.offer(task);
    }

    @Override
    public Task process() {
        return this.tasks.poll();
    }

    @Override
    public Task peek() {
        return this.tasks.peek();
    }

    @Override
    public Boolean contains(Task task) {
        return this.tasks.contains(task);
    }

    @Override
    public int size() {
        return this.tasks.size();
    }

    @Override
    public Boolean remove(Task task) {
        if (!tasks.contains(task)) {
            throw new IllegalArgumentException();
        }
        return true;
    }

    @Override
    public Boolean remove(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return tasks.remove(task);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void insertBefore(int id, Task task) {
        if (!tasks.contains(task)) {
            throw new IllegalArgumentException();
        }
        List<Task> tasksToPutBackIn = new ArrayList<>();
        while (tasks.peek().getId() != id) {
            tasksToPutBackIn.add(tasks.pop());
        }
        tasks.push(task);
        for (int i = tasksToPutBackIn.size() - 1; i >= 0; i--) {
            tasks.push(tasksToPutBackIn.get(i));
        }

    }

    @Override
    public void insertAfter(int id, Task task) {
        List<Task> tasksToPutBackIn = new ArrayList<>();
        Task current = tasks.peek();
        while (current != null && current.getId() != id) {
            tasksToPutBackIn.add(tasks.poll());
            current = tasks.peek();
        }

        if (current == null) {
            throw new IllegalArgumentException();
        } else {
            tasksToPutBackIn.add(tasks.poll());
        }

        tasksToPutBackIn.add(task);

        while (!tasksToPutBackIn.isEmpty()) {
            this.tasks.addFirst(tasksToPutBackIn.remove(tasksToPutBackIn.size() - 1));
        }
    }

    @Override
    public void clear() {
        this.tasks.clear();
    }

    @Override
    public Task[] toArray() {
        Task[] result = new Task[tasks.size()];
        return this.tasks.toArray(result);
    }

    @Override
    public void reschedule(Task first, Task second) {
        List<Task> tasks = toList();

        int firstIndex = tasks.indexOf(first);
        int secondIndex = tasks.indexOf(second);

        if (firstIndex == -1 || secondIndex == -1) {
            throw new IllegalArgumentException();
        }

        Collections.swap(tasks, firstIndex, secondIndex);

        this.tasks = new ArrayDeque<>(tasks);
    }

    @Override
    public List<Task> toList() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void reverse() {
        Deque<Task> reversed = new ArrayDeque<>();
        for (Task task : tasks) {
            reversed.push(task);
        }
        tasks = reversed;
    }

    @Override
    public Task find(int id) {
        for (Task currentTask : tasks) {
            if (currentTask.getId() == id) {
                return currentTask;
            }
        }
        throw new IllegalArgumentException();
    }


    @Override
    public Task find(Task task) {
        for (Task currentTask : tasks) {
            if (currentTask == task) {
                return currentTask;
            }
        }
        throw new IllegalArgumentException();
    }

}
