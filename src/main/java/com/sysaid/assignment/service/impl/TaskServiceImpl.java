package com.sysaid.assignment.service.impl;

import com.sysaid.assignment.model.Task;
import com.sysaid.assignment.model.User;
import com.sysaid.assignment.service.TaskService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final Map<String, User> users = new HashMap<>();
    private final List<Task> allTasks = new ArrayList<>();

    @PostConstruct
    private void init() {
        fillTasks();

        User defaultUser = new User("defaultUser");
        defaultUser.getTasks().addAll(allTasks);
        users.put("defaultUser", defaultUser);
    }

    private void fillTasks() {
        for (int i = 1; i <= 10; i++) {
            Task task = new Task();
            task.setId("taskId-" + i);
            task.setType(i % 2 == 0 ? "testType1" : "testType2");
            task.setName("taskName-" + i);
            allTasks.add(task);
        }
    }

    private User getUser(String username) {
        return Optional.ofNullable(users.get(username))
                .orElseThrow(() -> new NoSuchElementException("User not found: " + username));
    }

    private Task getTaskById(User user, String taskId) {
        return user.getTasks().stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Task not found with ID: " + taskId));
    }

    @Override
    public List<Task> getTasks(String username, String type) {
        User user = getUser(username);
        return user.getTasks().stream()
                .filter(task -> task.getType().equals(type) && !task.isCompleted())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getWishList(String username) {
        User user = getUser(username);
        return user.getTasks().stream()
                .filter(Task::isWishList)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getCompletedTasks(String username) {
        User user = getUser(username);
        return user.getTasks().stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public void markTaskAsWishList(String username, String taskId) {
        User user = getUser(username);
        Task task = getTaskById(user, taskId);
        if (!task.isWishList()) {
            task.setWishList(true);
            task.setRating(task.getRating() + 1);
        }
    }

    @Override
    public void markTaskAsCompleted(String username, String taskId) {
        User user = getUser(username);
        Task task = getTaskById(user, taskId);
        if (!task.isCompleted()) {
            task.setCompleted(true);
            task.setRating(task.getRating() + 2);
        }
    }

    @Override
    public Task getTaskOfTheDay() {
        return allTasks.isEmpty() ? null : allTasks.get(ThreadLocalRandom.current().nextInt(allTasks.size()));
    }

    @Override
    public Task getRatedTask() {
        List<Task> sortedTasks = allTasks.stream()
                .sorted(Comparator.comparingInt(Task::getRating).reversed())
                .collect(Collectors.toList());

        int random = ThreadLocalRandom.current().nextInt(100);
        if (sortedTasks.isEmpty()) {
            return getTaskOfTheDay();
        }

        return selectTaskBasedOnProbability(sortedTasks, random);
    }

    private Task selectTaskBasedOnProbability(List<Task> sortedTasks, int random) {
        if (random < 20) {
            return getTaskAtIndex(sortedTasks, 0);
        } else if (random < 40) {
            return getTaskAtIndex(sortedTasks, 1);
        } else if (random < 50) {
            return getTaskAtIndex(sortedTasks, 2);
        } else if (random < 55) {
            return getTaskAtIndex(sortedTasks, 3);
        } else if (random < 60) {
            return getTaskAtIndex(sortedTasks, 4);
        } else {
            return getTaskOfTheDay();
        }
    }

    private Task getTaskAtIndex(List<Task> tasks, int index) {
        return index < tasks.size() ? tasks.get(index) : getTaskOfTheDay();
    }
}
