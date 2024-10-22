package com.application.mongodb_api.aggregation;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.application.mongodb_api.dto.OrderItem;


public class OrderResponse {
    
    

    private String clientId;
    private List<OrderItem> items;

    public OrderResponse(String clientId, List<OrderItem> items) {
        this.clientId = clientId;
        this.items = items;
    }

    public OrderResponse() {
    }

  

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setOrderProducts(List<OrderItem> items) {
        this.items = items;
    }

    
}
