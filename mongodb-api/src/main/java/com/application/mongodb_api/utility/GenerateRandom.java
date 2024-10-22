package com.application.mongodb_api.utility;

import java.security.SecureRandom;

public class GenerateRandom {
    private SecureRandom rand = new SecureRandom();
    private int upperbound = 50000;

    // Constructor to initialize upperbound
    public GenerateRandom(int upperbound) {
        this.upperbound = upperbound;
    }

    public GenerateRandom() {
    }

    // Getter for upperbound
    public int getUpperbound() {
        return upperbound;
    }

    // Setter for upperbound
    public void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    // Method to generate random int using the upperbound
    public int generateRandomInt() {
        return rand.nextInt(upperbound);
    }
}
