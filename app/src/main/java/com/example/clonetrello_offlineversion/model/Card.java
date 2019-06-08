package com.example.clonetrello_offlineversion.model;

public class Card {
    private int boardID;
    private int cardID;
    private String cardName;
    private String cardDescription;
    private String cardDateEnd;
    private String cardTimeEnd;
    private int checkDateTime;

    public Card() {
    }

    public Card(int boardID, String cardName, int checkDateTime) {
        this.boardID = boardID;
        this.cardName = cardName;
        this.checkDateTime = checkDateTime;
    }

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public void setCardDescription(String cardDescription) {
        this.cardDescription = cardDescription;
    }

    public String getCardDateEnd() {
        return cardDateEnd;
    }

    public void setCardDateEnd(String cardDateEnd) {
        this.cardDateEnd = cardDateEnd;
    }

    public String getCardTimeEnd() {
        return cardTimeEnd;
    }

    public void setCardTimeEnd(String cardTimeEnd) {
        this.cardTimeEnd = cardTimeEnd;
    }

    public int getCheckDateTime() {
        return checkDateTime;
    }

    public void setCheckDateTime(int checkDateTime) {
        this.checkDateTime = checkDateTime;
    }
}
