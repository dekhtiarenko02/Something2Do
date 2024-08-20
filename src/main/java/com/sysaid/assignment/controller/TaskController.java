package com.sysaid.assignment.controller;

import com.sysaid.assignment.model.Task;
import com.sysaid.assignment.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Task>> getTasksByUsernameAndType(
            @PathVariable String username,
            @RequestParam String type) {
        List<Task> tasks = taskService.getTasks(username, type);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{username}/wishList")
    public ResponseEntity<Void> markTaskAsWishListByUsernameAndTaskId(
            @PathVariable String username,
            @RequestParam String taskId) {
        taskService.markTaskAsWishList(username, taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{username}/completed")
    public ResponseEntity<Void> markTaskAsCompletedByUsernameAndTaskId(
            @PathVariable String username,
            @RequestParam String taskId) {
        taskService.markTaskAsCompleted(username, taskId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{username}/wishList")
    public ResponseEntity<List<Task>> getWishList(@PathVariable String username) {
        List<Task> tasks = taskService.getWishList(username);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{username}/completedTasks")
    public ResponseEntity<List<Task>> getCompletedTasks(@PathVariable String username) {
        List<Task> tasks = taskService.getCompletedTasks(username);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/taskOfTheDay")
    public ResponseEntity<Task> getTaskOfTheDay() {
        Task task = taskService.getTaskOfTheDay();
        return ResponseEntity.ok(task);
    }

    @GetMapping("/ratedTask")
    public ResponseEntity<Task> getRatedTask() {
        Task task = taskService.getRatedTask();
        return ResponseEntity.ok(task);
    }
}

