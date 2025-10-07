package com.sipplanner.model;

public class InvestmentResult {
    private String name; // Bank name / MF / Stock symbol
    private double maturity;
    private boolean recommended;
    private double price; // For stocks
    private String code; // For MF

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMaturity() { return maturity; }
    public void setMaturity(double maturity) { this.maturity = maturity; }

    public boolean isRecommended() { return recommended; }
    public void setRecommended(boolean recommended) { this.recommended = recommended; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
