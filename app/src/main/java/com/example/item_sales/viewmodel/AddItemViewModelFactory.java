package com.example.item_sales.viewmodel;

import android.app.Application;

import com.example.item_sales.model.CustomerDataBase;
import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.model.CustomerItemDataBase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddItemViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private CustomerDataBase customerDataBase;
    private Application application;
    private List<CustomerItemDataBase> customerItems;

    public AddItemViewModelFactory(CustomerDataBase model, Application application, List<CustomerItemDataBase> customerItems) {
        this.application = application;
        customerDataBase = model;
        this.customerItems = customerItems;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddItemViewModel(customerDataBase, application, customerItems);
    }
}
