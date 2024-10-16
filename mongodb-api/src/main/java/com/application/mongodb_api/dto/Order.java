package com.application.mongodb_api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Order {

    @Id
    String id;

    String clientID;
    List<OrderItem> items;

    public Order(String ID, String clientID, List<OrderItem> items) {
        this.id = ID;
        this.clientID = clientID;
        this.items = items;
    }

    public Order() {
    }

    public String getID() {
        return id;
    }

    public void setID(String orderId) {
        this.id = orderId;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setOrderProducts(List<OrderItem> items) {
        this.items = items;
    }
}
