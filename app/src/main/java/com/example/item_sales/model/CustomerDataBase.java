package com.example.item_sales.model;

import com.example.item_sales.database.TypeConverter;

import java.io.Serializable;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "customerDatabase")
public class CustomerDataBase implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id_customer;
    private String name_customer;
    private double total_amount_customer;
    private double balance_amount_customer;
    private String date_customer;
    private int quantitiy;

    @TypeConverters(TypeConverter.class)
    private List<CustomerItemDataBase> customerItems;  // custom type object

    public List<CustomerItemDataBase> getCustomerItems() {
        return customerItems;
    }

    public void setCustomerItems(List<CustomerItemDataBase> customerItems) {
        this.customerItems = customerItems;
    }

    public int getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(int quantitiy) {
        this.quantitiy = quantitiy;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public double getTotal_amount_customer() {
        return total_amount_customer;
    }

    public void setTotal_amount_customer(double total_amount_customer) {
        this.total_amount_customer = total_amount_customer;
    }

    public double getBalance_amount_customer() {
        return balance_amount_customer;
    }

    public void setBalance_amount_customer(double balance_amount_customer) {
        this.balance_amount_customer = balance_amount_customer;
    }

    public String getDate_customer() {
        return date_customer;
    }

    public void setDate_customer(String date_customer) {
        this.date_customer = date_customer;
    }

}
