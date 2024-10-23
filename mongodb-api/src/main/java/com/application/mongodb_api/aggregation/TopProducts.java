package com.application.mongodb_api.aggregation;

public class TopProducts {
    private String productId;
    private String name;
    private long quantity;

    public TopProducts() {}

    public TopProducts(String productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
