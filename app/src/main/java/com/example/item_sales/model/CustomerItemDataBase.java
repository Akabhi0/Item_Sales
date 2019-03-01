package com.example.item_sales.model;

import java.io.Serializable;
public class CustomerItemDataBase implements Serializable {

    private String itemName;
    private String quantity;
    private String rate;
    private double rateAmount;
    private double sgstAmount;
    private double cgstAmount;
    private double totalAmount;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getRateAmount() {
        return rateAmount;
    }

    public void setRateAmount(double rateAmount) {
        this.rateAmount = rateAmount;
    }

    public double getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(double sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public double getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(double cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
