package com.application.mongodb_api.aggregation;

public class ClientsByOrders {
    private String clientID;
    private long totalOrders;

    // Constructors
    public ClientsByOrders() {}

    public ClientsByOrders(String clientID, long totalOrders) {
        this.clientID = clientID;
        this.totalOrders = totalOrders;
    }

    // Getters and Setters
    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientId) {
        this.clientID = clientId;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
    
}
