package com.application.mongodb_api.aggregation;

public class ClientsByOrders {
    private String id;
    private String name;
    private long totalOrders;

    // Constructors
    public ClientsByOrders() {}

    public ClientsByOrders(String id, long totalOrders) {
        this.id = id;
        this.totalOrders = totalOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }
    
}
