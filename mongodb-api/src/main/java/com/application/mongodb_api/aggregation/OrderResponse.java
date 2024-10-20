package com.application.mongodb_api.aggregation;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.application.mongodb_api.dto.OrderItem;


public class OrderResponse {
    
    

    private String clientID;
    private List<OrderItem> items;

    public OrderResponse(String clientID, List<OrderItem> items) {
        this.clientID = clientID;
        this.items = items;
    }

    public OrderResponse() {
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
