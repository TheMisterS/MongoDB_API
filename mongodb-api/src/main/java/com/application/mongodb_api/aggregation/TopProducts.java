package com.application.mongodb_api.aggregation;

public class TopProducts {
    private String productId;
    private long quantity;

    // Constructors
    public TopProducts() {}

    public TopProducts(String productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
    
}
