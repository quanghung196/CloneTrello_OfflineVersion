package com.example.clonetrello_offlineversion.model;

public class ListWorks {
    private int cardID;
    private int listWorksID;
    private String listWorksName;

    private int clickCount;

    public ListWorks() {
    }

    public ListWorks(int cardID, String listWorksName) {
        this.cardID = cardID;
        this.listWorksName = listWorksName;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getListWorksID() {
        return listWorksID;
    }

    public void setListWorksID(int listWorksID) {
        this.listWorksID = listWorksID;
    }

    public String getListWorksName() {
        return listWorksName;
    }

    public void setListWorksName(String listWorksName) {
        this.listWorksName = listWorksName;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
