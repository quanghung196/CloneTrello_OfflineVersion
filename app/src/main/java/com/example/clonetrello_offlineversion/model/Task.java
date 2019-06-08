package com.example.clonetrello_offlineversion.model;

public class Task {
    private int taskId;
    private String taskName;
    private int taskColor;

    public Task() {
    }

    public Task(String taskName, int taskColor) {
        this.taskName = taskName;
        this.taskColor = taskColor;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskColor() {
        return taskColor;
    }

    public void setTaskColor(int taskColor) {
        this.taskColor = taskColor;
    }
}
