package com.example.clonetrello_offlineversion.model;

public class Work {

    private int carID;
    private int listWorksID;
    private int worksID;
    private int workDoneYet;
    private String worksName;

    public Work() {
    }

    public Work(int carID, int listWorksID, int workDoneYet, String worksName) {
        this.carID = carID;
        this.listWorksID = listWorksID;
        this.workDoneYet = workDoneYet;
        this.worksName = worksName;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getWorkDoneYet() {
        return workDoneYet;
    }

    public void setWorkDoneYet(int workDoneYet) {
        this.workDoneYet = workDoneYet;
    }

    public int getListWorksID() {
        return listWorksID;
    }

    public void setListWorksID(int listWorksID) {
        this.listWorksID = listWorksID;
    }

    public int getWorksID() {
        return worksID;
    }

    public void setWorksID(int worksID) {
        this.worksID = worksID;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }
}
