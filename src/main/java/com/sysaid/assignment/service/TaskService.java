package com.sysaid.assignment.service;

import com.sysaid.assignment.model.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTasks(String username, String type);

    List<Task> getWishList(String username);

    List<Task> getCompletedTasks(String username);

    void markTaskAsWishList(String username, String taskId);

    void markTaskAsCompleted(String username, String taskId);

    Task getTaskOfTheDay();

    Task getRatedTask();
}
