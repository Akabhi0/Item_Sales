package com.example.item_sales.database;

import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.model.CustomerItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertCustomerData(CustomerDataBase customerDataBase);

    @Insert
    void insertCustomerItem(CustomerItem customerItem);

    @Query("DELETE FROM customerDatabase WHERE id_customer=:id")
    void deleteAllCustomerData(int id);

    @Query("SELECT * FROM customerDatabase ORDER BY id_customer DESC")
    LiveData<List<CustomerDataBase>> getAllCustomer();

    @Query("SELECT * FROM customerDatabase WHERE (balance_amount_customer > 0) ORDER BY id_customer DESC")
    LiveData<List<CustomerDataBase>> getAllPartialCustomer();

    @Query("SELECT * FROM customerDatabase WHERE (balance_amount_customer <= 0) ORDER BY id_customer DESC")
    LiveData<List<CustomerDataBase>> getAllPaidCustomer();

    @Query("SELECT * FROM CustomerItem ORDER BY id DESC")
    LiveData<List<CustomerItem>> getAllCustomerItem();

    @Query("DELETE  FROM CustomerItem")
    void deleteAllCustomerItem();

    @Query("UPDATE customerDatabase SET balance_amount_customer=:balance_amount_customer WHERE id_customer=:id_customer")
    void getAllUpdatedData(int id_customer, double balance_amount_customer);
}
