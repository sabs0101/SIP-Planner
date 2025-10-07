package com.sipplanner.model;

public class Bank {
    private String name;
    private double rate;

    public Bank(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() { return name; }
    public double getRate() { return rate; }
}
