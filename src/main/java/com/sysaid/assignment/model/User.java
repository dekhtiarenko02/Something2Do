package com.sysaid.assignment.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private String username;
    private List<Task> tasks;

    public User(String username) {
        this.username = username;
        this.tasks = new ArrayList<>();
    }
}
