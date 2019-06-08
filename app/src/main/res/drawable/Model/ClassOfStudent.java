package com.example.sqlite_with_intent.Model;

public class ClassOfStudent {
    private int classID;
    private String className;
    private int classColor;

    public ClassOfStudent() {
    }

    public ClassOfStudent(String className, int classColor) {
        this.className = className;
        this.classColor = classColor;
    }

    public ClassOfStudent(int classID, String className, int classColor) {
        this.classID = classID;
        this.className = className;
        this.classColor = classColor;
    }

    public int getClassColor() {
        return classColor;
    }

    public void setClassColor(int classColor) {
        this.classColor = classColor;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
