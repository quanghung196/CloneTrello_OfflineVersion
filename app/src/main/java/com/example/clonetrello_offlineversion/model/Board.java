package com.example.clonetrello_offlineversion.model;

public class Board {
    private int taskID;
    private int boardId;
    private String boardName;

    public Board() {
    }

    public Board(int taskID, String boardName) {
        this.taskID = taskID;
        this.boardName = boardName;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
