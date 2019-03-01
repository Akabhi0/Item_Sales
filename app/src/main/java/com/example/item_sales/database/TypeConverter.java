package com.example.item_sales.database;

import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.model.CustomerItemDataBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeConverter {
    @androidx.room.TypeConverter
    public String fromCustomerItemValuesList(List<CustomerItemDataBase> customerItems) {
        if (customerItems == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CustomerItemDataBase>>() {
        }.getType();
        String json = gson.toJson(customerItems, type);
        return json;
    }

    @androidx.room.TypeConverter // note this annotation
    public List<CustomerItemDataBase> toCustomerItemValuesList(String customerItemString) {
        if (customerItemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<CustomerItemDataBase>>() {
        }.getType();
        List<CustomerItemDataBase> productCategoriesList = gson.fromJson(customerItemString, type);
        return productCategoriesList;
    }
}
