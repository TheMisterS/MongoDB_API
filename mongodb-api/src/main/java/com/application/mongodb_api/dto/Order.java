package com.application.mongodb_api.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Order {

    @Id
    String id;

    String clientId;
    List<OrderItem> items;

    public Order(String ID, String clientId, List<OrderItem> items) {
        this.id = ID;
        this.clientId = clientId;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientID(String clientId) {
        this.clientId = clientId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setOrderProducts(List<OrderItem> items) {
        this.items = items;
    }

}
