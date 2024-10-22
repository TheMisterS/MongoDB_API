package com.application.mongodb_api.utility;

public class ItemID {
    String stringID;
    int id;

    public ItemID(String givenStringID) {
        this.stringID = givenStringID;
        id = Integer.parseInt(stringID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
